import com.checkers.client.CheckersBot;
import com.checkers.domain.vo.Check;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Step;
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
        List<Step> stepList = new ArrayList<Step>();
        for(Check check : fieldUtil.getWhiteChecks(field)){
            stepList.addAll(stepCalculator.validSteps(field, check));
        }
        List<Step> stepsForHeat = stepCalculator.getHeatSteps(field, stepList);
        return stepsForHeat.isEmpty() ?
                stepList.get(random.nextInt(stepList.size() - 1)) :
                stepsForHeat.get(stepsForHeat.size() == 1 ? 0 : random.nextInt(stepsForHeat.size() - 1));
    }

}
