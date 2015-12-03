import com.checkers.client.CheckersBot;
import com.checkers.domain.vo.Check;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Position;
import com.checkers.domain.vo.Step;
import org.w3c.dom.html.HTMLDirectoryElement;
import steps.CheckersRulesHolder;
import steps.StepCalculator;
import utils.FieldUtil;

import java.util.*;

/**
 * Created by Eugene on 03.12.2015.
 */
public class SuperBot implements CheckersBot{

    CheckersRulesHolder checkersRulesHolder;
    StepCalculator stepCalculator;
    FieldUtil fieldUtil;
    Random random;

    public SuperBot(){
        random = new Random();
        fieldUtil = new FieldUtil();
        checkersRulesHolder = new CheckersRulesHolder();
        stepCalculator = new StepCalculator(checkersRulesHolder, fieldUtil);
    }

    @Override
    public Step calculateNextStep(Field field) {
        List<Step> stepList = new ArrayList<Step>();
        Map<Check, List<Step>> checkSteps = new HashMap<Check, List<Step>>();
        for(Check check : fieldUtil.getWhiteChecks(field)){
            List<Step> steps = stepCalculator.validSteps(field, check);
            stepList.addAll(steps);
            if(steps.size()>0)
            checkSteps.put(check, steps);
        }
        List<Step> stepsForHeat = longest(stepCalculator.getHeatSteps(field, stepList));
        System.out.println(checkSteps.size());
        for(Check ch: checkSteps.keySet()) {
            for(Step st: checkSteps.get(ch)) {
                List<Field> nextFields = getFieldAfterStep(field, ch, st);
                System.out.println(nextFields.size());
            }
        }
        return stepsForHeat.isEmpty() ?
                stepList.get(stepList.size() == 1 ? 0 : random.nextInt(stepList.size() - 1)) :
                stepsForHeat.get(stepsForHeat.size() == 1 ? 0 : random.nextInt(stepsForHeat.size() - 1));
    }

    private List<Field> getFieldAfterStep(Field oldField, Check check, Step step){
        List<Field> result = new ArrayList<Field>();
        ArrayList<Check> fieldChecks = new ArrayList<Check>(oldField.getAllChecks());
        for(int i=0;i<fieldChecks.size();++i){
            if (fieldChecks.get(i) == check) {
                Position old = check.getPosition();
                for (Position pos : step.getPositionAfterMove()) {
                    fieldChecks.get(i).setPosition(pos);
                    Field f = new Field();
                    f.setAllChecks(new HashSet<Check>(fieldChecks));
                    result.add(f);
                }
            }
        }
        return result;
    }

    private List<Step> longest(List<Step> stepsForHeat) {
        int length = -1;
        List<Step> steps = new ArrayList<Step>();
        for (Step stepInner : stepsForHeat){
            if(stepInner.getPositionAfterMove().size() > length){
                length = stepInner.getPositionAfterMove().size();
                steps.clear();
            }
            if(stepInner.getPositionAfterMove().size() == length){
                steps.add(stepInner);
            }
        }
        return steps;
    }

}
