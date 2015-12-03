import com.checkers.client.CheckersBot;
import com.checkers.domain.vo.Check;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Step;
import rating.FieldTree;
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
    FieldTree tree;

    public SuperBot(){
        random = new Random();
        fieldUtil = new FieldUtil();
        checkersRulesHolder = new CheckersRulesHolder();
        stepCalculator = new StepCalculator(checkersRulesHolder, fieldUtil);
    }

    @Override
    public Step calculateNextStep(Field field) {
        List<Step> stepList = new ArrayList<Step>();
        for(Check check : fieldUtil.getWhiteChecks(field)){
            stepList.addAll(stepCalculator.validSteps(field, check));
        }
        tree = new FieldTree(field);
        tree.getBestField();
        List<Step> stepsForHeat = longest(stepCalculator.getHeatSteps(field, stepList));
        List<Step> longest = longest(stepsForHeat);
        return stepsForHeat.isEmpty() ?
                stepList.get(stepList.size() == 1 ? 0 : random.nextInt(stepList.size() - 1)) :
                longest.isEmpty() ?
                stepsForHeat.get(stepsForHeat.size() == 1 ? 0 : random.nextInt(stepsForHeat.size() - 1)) :
                        longest.get(longest.size() == 1 ? 0 : random.nextInt(longest.size() - 1)) ;
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
