import com.checkers.client.CheckersBot;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Step;
import steps.CheckersRulesHolder;
import steps.StepCalculator;

/**
 * Created by Eugene on 03.12.2015.
 */
public class SuperBot implements CheckersBot{

    StepCalculator stepCalculator;

    public SuperBot(){
        stepCalculator = new StepCalculator(new CheckersRulesHolder());
    }

    @Override
    public Step calculateNextStep(Field field) {

        return null;
    }

}
