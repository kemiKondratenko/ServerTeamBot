package rating.impl;

import com.checkers.domain.vo.Field;
import rating.Rater;
import steps.StepCalculator;

/**
 * Created by Eugene on 04.12.2015.
 */
public class AmountOfHeatSteps implements Rater{

    StepCalculator stepCalculator;

    public AmountOfHeatSteps(StepCalculator stepCalculator) {
        this.stepCalculator = stepCalculator;
    }

    @Override
    public int rate(Field field) {
        return stepCalculator.getHeatSteps(field, stepCalculator.validSteps(field)).size();
    }
}
