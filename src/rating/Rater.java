package rating;

import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Step;

/**
 * Created by Eugene on 03.12.2015.
 */
public interface Rater {
    int rate(Field field, Step step);
}
