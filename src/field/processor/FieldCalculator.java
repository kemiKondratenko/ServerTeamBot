package field.processor;

import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Step;
import rating.RatingFinal;
import steps.CheckersRulesHolder;
import steps.StepCalculator;
import utils.FieldUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eugene on 04.12.2015.
 */
public class FieldCalculator {

    Field fieldLocal;
    int DEEP;
    StepCalculator stepCalculator;
    FieldUtil fieldUtil;
    CheckersRulesHolder checkersRulesHolder;
    RatingFinal ratingFinal;

    public FieldCalculator(Field field, int deep) {
        this.fieldLocal = field;
        this.DEEP = deep;
        fieldUtil = new FieldUtil();
        checkersRulesHolder = new CheckersRulesHolder();
        stepCalculator = new StepCalculator(checkersRulesHolder, fieldUtil);
        ratingFinal = new RatingFinal(fieldUtil, stepCalculator);
    }
    public Step getBestStep(Field fieldLocal){
        return getBestStep(fieldLocal, 0).getValue();
    }

    public Map.Entry<Double, Step> getBestStep(Field fieldLocal, int deepness){
        Map<Double, Step> stepRate = getStepRate(fieldLocal, deepness);
        double res = getStep(stepRate, deepness);
        System.out.println("Res "+res);
        for (Map.Entry<Double, Step>  doubleStepEntry : stepRate.entrySet()){
            if (doubleStepEntry.getKey().equals(res))
                return doubleStepEntry;
        }
        return null;
    }

    private Double getStep(Map<Double, Step> stepRate, int deepness) {
        if(deepness % 2 == 0){
            return getMaxStep(stepRate);
        }else {
            return getMinStep(stepRate);
        }
    }

    private double getMaxStep(Map<Double, Step> stepRate){
        double max = Double.MIN_VALUE;
        for(Double res : stepRate.keySet()){
            if(res > max){
                max = res;
            }
        }
        return max;
    }

    private double getMinStep(Map<Double, Step> stepRate){
        double min = Double.MAX_VALUE;
        for(Double res : stepRate.keySet()){
            if(res < min){
                min = res;
            }
        }
        return min;
    }

    private Map<Double, Step> getStepRate(Field fieldLocal, int deepness) {
        List<Step> stepList = stepCalculator.validSteps(fieldLocal);
        Map<Double, Step> result = new HashMap<Double, Step>();
        for(Step step : stepList){
            double rate = rateStep(fieldLocal, step, deepness);
            System.out.println("Rate "+rate);
            result.put(rateStep(fieldLocal, step, deepness), step);
        }
        return result;
    }

    private Double rateStep(Field fieldLocal, Step step, int deepness) {
        double result = ratingFinal.rate(fieldLocal);
        System.out.println("DOwn rate "+result);
        if(deepness++ >= DEEP){
            return result;
        }
        Field fieldNew = fieldUtil.copy(fieldLocal);
        Step stepNew = fieldUtil.copy(step);
        checkersRulesHolder.calculateNextField(fieldNew, stepNew);
        Map.Entry<Double, Step> doubleStepEntry =  getBestStep(fieldNew, deepness);
        if(doubleStepEntry != null) {
            System.out.println("DOwn FUKCU rate "+doubleStepEntry.getKey());
            result += doubleStepEntry.getKey();
        }
        return result;
    }
}
