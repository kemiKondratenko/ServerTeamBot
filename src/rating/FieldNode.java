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
public class FieldNode {

    CheckersRulesHolder checkersRulesHolder;
    StepCalculator stepCalculator;
    FieldUtil fieldUtil;

    private static final int MAX_DEPTH = 2;
    private static final int MIN_DEPTH = 0;

    private int raiting;
    private int deep;
    private int checksAmountCanGo;
    private Field root;
    private List<FieldNode> oponentsFields;

    public FieldNode(Field root, int deep){
        fieldUtil = new FieldUtil();
        checkersRulesHolder = new CheckersRulesHolder();
        stepCalculator = new StepCalculator(checkersRulesHolder, fieldUtil);
        createNode(root, deep + 1);
    }

    public List<FieldNode> getOponentsFields() {
        return oponentsFields;
    }

    public int getChecksAmountCanGo() {
        return checksAmountCanGo;
    }

    public int getRaiting() {
        return raiting;
    }

    private void createNode(Field root, int deep){
        this.root = root;
        int white = fieldUtil.getWhiteChecks(root).size();
        int red = root.getAllChecks().size() - fieldUtil.getWhiteChecks(root).size();
        this.raiting = white - red;
        if(deep>MAX_DEPTH)
            return;
        oponentsFields = new ArrayList<FieldNode>();
        List<Field> next = calculateNextFields(root);
        for(Field f: next){
            checkersRulesHolder.revert(f);
            oponentsFields.add(new FieldNode(f, deep+1));
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
        checksAmountCanGo = checkSteps.keySet().size();
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

    public Field getRoot() {
        return root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldNode fieldNode = (FieldNode) o;

        if (checksAmountCanGo != fieldNode.checksAmountCanGo) return false;
        if (deep != fieldNode.deep) return false;
        if (raiting != fieldNode.raiting) return false;
        if (oponentsFields != null ? !oponentsFields.equals(fieldNode.oponentsFields) : fieldNode.oponentsFields != null)
            return false;
        if (!root.equals(fieldNode.root)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = raiting;
        result = 31 * result + deep;
        result = 31 * result + checksAmountCanGo;
        result = 31 * result + root.hashCode();
        result = 31 * result + (oponentsFields != null ? oponentsFields.hashCode() : 0);
        return result;
    }
}
