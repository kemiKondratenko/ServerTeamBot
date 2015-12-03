package rating.impl;

import com.checkers.domain.vo.Field;
import rating.Rater;
import utils.FieldUtil;

/**
 * Created by Eugene on 04.12.2015.
 */
public class AmountOfChecks implements Rater{

    FieldUtil fieldUtil;

    public AmountOfChecks(FieldUtil fieldUtil) {
        this.fieldUtil = fieldUtil;
    }

    @Override
    public int rate(Field field) {
        int my = fieldUtil.getWhiteChecks(field).size();
        int his = fieldUtil.getWhiteChecks(field).size() - field.getAllChecks().size();
        return my - his;
    }
}
