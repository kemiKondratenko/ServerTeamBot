package utils;

import com.checkers.domain.vo.Check;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Position;
import com.checkers.domain.vo.Step;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Eugene on 03.12.2015.
 */
public class FieldUtil {

    public List<Check> getWhiteChecks(Field field) {
        List<Check> checks = new ArrayList<Check>();
        for(Check check: field.getAllChecks()){
            if(check.getColor() == 0)
                checks.add(check);
        }
        return checks;
    }

    public Field copy(Field field) {
        Field fieldRes = new Field();
        fieldRes.setAllChecks(copy(field.getAllChecks()));
        return fieldRes;
    }

    public Set<Check> copy(Set<Check> allChecks) {
        Set<Check> checks = new HashSet<Check>();
        for(Check check : allChecks){
            checks.add(copy(check));
        }
        return checks;
    }

    public Check copy(Check check) {
        Check checkRes = new Check(copy(check.getPosition()), check.getColor());
        checkRes.setQueen(check.isQueen());
        return checkRes;
    }

    public List<Position> copy(List<Position> positionAfterMove) {
        List<Position> positions = new ArrayList<Position>();
        for(Position position : positionAfterMove){
            positions.add(copy(position));
        }
        return positions;
    }

    public Position copy(Position position) {
        return new Position(position.getX(), position.getY());
    }

    public Step copy(Step step) {
        return new Step(copy(step.getCheck()), (ArrayList<Position>) copy(step.getPositionAfterMove()));
    }
}
