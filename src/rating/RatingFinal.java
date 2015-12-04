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
public class RatingFinal implements Rater{

    Map<Double, Rater> raters;

    public RatingFinal(FieldUtil fieldUtils, StepCalculator stepCalculator) {
        raters = new HashMap<Double, Rater>();
        raters.put(1., new AmountOfChecks(fieldUtils));
        raters.put(1., new AmountOfSteps(stepCalculator));
        raters.put(1., new AmountOfSimpleCheks(fieldUtils));
        raters.put(1., new AmountOfQueen(fieldUtils));
        raters.put(1., new AmountOfHeatSteps(stepCalculator));
    }

    @Override
    public int rate(Field field) {
        int res = 0;
        for(Map.Entry<Double, Rater> rater : raters.entrySet()){
            res += rater.getKey() * rater.getValue().rate(field);
        }
        return res;
    }
}
