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
    public double rate(Field field) {
        double my = fieldUtil.getWhiteChecks(field).size();
        double his = field.getAllChecks().size() - fieldUtil.getWhiteChecks(field).size();
        return my - his;
    }
}
