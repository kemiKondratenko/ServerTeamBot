package rating.impl;

import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Check;
import rating.Rater;
import utils.FieldUtil;

/**
 * Created by Eugene on 04.12.2015.
 */
public class AmountOfSimpleCheks implements Rater{

    FieldUtil fieldUtil;

    public AmountOfSimpleCheks(FieldUtil fieldUtil) {
        this.fieldUtil = fieldUtil;
    }

    @Override
    public double rate(Field field) {
        double res = 0;
        for(Check check : fieldUtil.getWhiteChecks(field)){
            if(!check.isQueen())
                res++;
        }
        return res;
    }
}
