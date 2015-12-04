import com.checkers.client.CheckersBot;
import com.checkers.domain.vo.Check;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Step;
import field.processor.FieldCalculator;
import steps.CheckersRulesHolder;
import steps.StepCalculator;
import utils.FieldUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        FieldCalculator fieldCalculator = new FieldCalculator(field, 2);
        return fieldCalculator.getBestStep(field);
    }

    private Step getStep(Field field, Field newField) {
        Check seemFrom = null;
        Check seemTo = null;

        for (Check check : fieldUtil.getWhiteChecks(field)){
            for (Check checkNew : fieldUtil.getWhiteChecks(newField)){
                if(!check.getPosition().equals(checkNew.getPosition())) {
                    seemFrom = check;
                    seemTo = checkNew;
                    break;
                }
            }
        }
        System.out.println("X "+seemFrom.getPosition().getX() +
        " Y "+ seemFrom.getPosition().getY());
        System.out.println("X "+seemTo.getPosition().getX() +
                " Y "+ seemTo.getPosition().getY());
        for (Step stepRes : stepCalculator.validSteps(field)){
            if(seemFrom.getPosition().equals(stepRes.getCheck().getPosition()) &&
                    seemTo.getPosition().equals(stepRes.getPositionAfterMove()
                            .get(stepRes.getPositionAfterMove().size() - 1))) {
                return stepRes;
            }
        }
        return null;
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
