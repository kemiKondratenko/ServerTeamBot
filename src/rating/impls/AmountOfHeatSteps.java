package rating.impls;

import com.checkers.domain.vo.Field;
import steps.CheckersRulesHolder;
import rating.Rater;
import steps.StepCalculator;

/**
 * Created by Eugene on 04.12.2015.
 */
public class AmountOfHeatSteps implements Rater{

    CheckersRulesHolder checkersRulesHolder;
    StepCalculator stepCalculator;

    public AmountOfHeatSteps(CheckersRulesHolder checkersRulesHolder, StepCalculator stepCalculator) {
        this.checkersRulesHolder = checkersRulesHolder;
        this.stepCalculator = stepCalculator;
    }

    @Override
    public int rate(Field field) {
        //stepCalculator.
        return 0;
    }
}
