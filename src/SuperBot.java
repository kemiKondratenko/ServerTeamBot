import com.checkers.client.CheckersBot;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Step;
import rating.FieldTree;
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
    FieldTree tree;


    public SuperBot(){
        random = new Random();
        fieldUtil = new FieldUtil();
        checkersRulesHolder = new CheckersRulesHolder();
        stepCalculator = new StepCalculator(checkersRulesHolder, fieldUtil);
    }

    @Override
    public Step calculateNextStep(Field field) {
        return stepCalculator.validSteps(field).get(0);
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
