package steps;

import com.checkers.domain.vo.Check;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Position;
import com.checkers.domain.vo.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugene on 03.12.2015.
 */
public class StepCalculator {
    CheckersRulesHolder checkersRulesHolder;

    public StepCalculator(CheckersRulesHolder checkersRulesHolder) {
        this.checkersRulesHolder = checkersRulesHolder;
    }

    public List<Step> validSteps(Field field, Check check){
        List<Position> positionList = collectPositions(check);
        positionList = validatePositions(field, check, positionList);
        List<Step> stepList = createSteps(field, check, positionList);
        return stepList;
    }

    private List<Step> createSteps(Field field, Check check, List<Position> positionList) {
        return null;
    }

    private List<Position> validatePositions(Field field, Check check, List<Position> positionList) {
        List<Position> positions = new ArrayList<Position>();
        for (Position position : positionList){
            if(checkersRulesHolder.calculateNextField(field, check, position, false))
                positions.add(position);
        }
        return positions;
    }

    private List<Position> collectPositions(Check check) {
        List<Position> positionList =new ArrayList<Position>();
        positionList.addAll(checkersRulesHolder.possibleHeatPositions(check.getPosition()));
        positionList.addAll(checkersRulesHolder.possibleSimpleStepPositions(check.getPosition()));
        return positionList;
    }

}
