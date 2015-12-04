package rating.impl;

import com.checkers.domain.vo.Field;
import rating.Rater;
import steps.StepCalculator;

/**
 * Created by Eugene on 04.12.2015.
 */
public class AmountOfSteps implements Rater{

    StepCalculator stepCalculator;

    public AmountOfSteps(StepCalculator stepCalculator) {
        this.stepCalculator = stepCalculator;
    }

    @Override
    public double rate(Field field) {
        return stepCalculator.validSteps(field).size();
    }
}
