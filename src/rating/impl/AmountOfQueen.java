package rating.impl;

import com.checkers.domain.vo.Check;
import com.checkers.domain.vo.Field;
import rating.Rater;
import utils.FieldUtil;

/**
 * Created by Eugene on 04.12.2015.
 */
public class AmountOfQueen implements Rater {

    FieldUtil fieldUtil;

    public AmountOfQueen(FieldUtil fieldUtil) {
        this.fieldUtil = fieldUtil;
    }

    @Override
    public int rate(Field field) {
        int res = 0;
        for(Check check : fieldUtil.getWhiteChecks(field)){
            if(check.isQueen())
                res++;
        }
        return res;
    }
}
