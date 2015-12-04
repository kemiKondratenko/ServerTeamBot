package rating;

import com.checkers.domain.vo.Field;
import rating.impl.*;
import steps.StepCalculator;
import utils.FieldUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eugene on 04.12.2015.
 */
public class RatingFinal{

    Map<Double, Rater> raters;

    public RatingFinal(FieldUtil fieldUtils, StepCalculator stepCalculator) {
        raters = new HashMap<Double, Rater>();
        raters.put(0.4, new AmountOfChecks(fieldUtils));
        raters.put(1.3, new AmountOfSteps(stepCalculator));
        raters.put(0.1, new AmountOfSimpleCheks(fieldUtils));
        raters.put(1.9, new AmountOfQueen(fieldUtils));
        raters.put(1.7, new AmountOfHeatSteps(stepCalculator));
    }

    public double rate(Field field) {
        double res = 0;
        for(Double rater : raters.keySet()){
            res += rater * raters.get(rater).rate(field);
        }
        return res;
    }
}
