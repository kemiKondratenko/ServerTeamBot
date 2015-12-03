package rating;

import com.checkers.domain.vo.Check;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Position;
import com.checkers.domain.vo.Step;
import steps.CheckersRulesHolder;
import steps.StepCalculator;
import utils.FieldUtil;

import java.util.*;

/**
 * Created by KutsykV on 04.12.2015.
 */
public class FieldTree {

    CheckersRulesHolder checkersRulesHolder;
    StepCalculator stepCalculator;
    FieldUtil fieldUtil;

    private Field root;
    private List<FieldTree> leafs;

    private void BuildTree(Field root){
        List<Field> next = calculateNextFields(root);
        for(Field f: next){
            calculateNextFields(f);
        }
    }

    private List<Field> calculateNextFields(Field field){
        Map<Check, List<Step>> checkSteps = new HashMap<Check, List<Step>>();
        List<Field> myNextFields = new ArrayList<Field>();
        for(Check check : fieldUtil.getWhiteChecks(field)){
            List<Step> steps = stepCalculator.validSteps(field, check);
            if(steps.size()>0)
                checkSteps.put(check, steps);
        }
        for(Check ch: checkSteps.keySet())
            for(Step st: checkSteps.get(ch))
                myNextFields.addAll(getFieldAfterStep(field, ch, st));
        return myNextFields;
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

}
