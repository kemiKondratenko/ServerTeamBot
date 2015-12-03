package steps;

import com.checkers.domain.vo.Check;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Position;
import com.checkers.domain.vo.Step;
import utils.FieldUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugene on 03.12.2015.
 */
public class StepCalculator {

    CheckersRulesHolder checkersRulesHolder;
    FieldUtil fieldUtil;

    public StepCalculator(CheckersRulesHolder checkersRulesHolder, FieldUtil fieldUtil) {
        this.checkersRulesHolder = checkersRulesHolder;
        this.fieldUtil = fieldUtil;
    }

    public List<Step> validSteps(Field field){
        List<Step> stepList = new ArrayList<Step>();
        for(Check check : fieldUtil.getWhiteChecks(field)){
            stepList.addAll(validSteps(field, check));
        }


        List<Step> stepsForHeat = getHeatSteps(field, stepList);

        return stepsForHeat.isEmpty() ?
                stepList :
                stepsForHeat;
    }

    public List<Step> validSteps(Field field, Check check){
        List<Position> positionList = collectPositions(check);
        positionList = validatePositions(field, check, positionList);
        List<Position> heatPositions = getHeatSteps(field, check, positionList);
        positionList = heatPositions.isEmpty() ? positionList : heatPositions;
        List<Step> stepList = createSteps(field, check, positionList, heatPositions);
        return stepList;
    }

    private List<Step> validHeats(Field field, Check check) {
        List<Position> positionList = collectPositions(check);
        positionList = validatePositions(field, check, positionList);
        positionList = getHeatSteps(field, check, positionList);
        List<Step> stepList = createSteps(field, check, positionList, positionList);
        return stepList;
    }

    private List<Step> createSteps(Field field, Check check,
                                   List<Position> positionList, List<Position> heatPositions) {
        List<Step> stepList = new ArrayList<Step>();
        for(Position position: positionList){
            ArrayList<Position> positions = new ArrayList<Position>();
            positions.add(position);
            stepList.add(new Step(check, positions));
        }
        if(!heatPositions.isEmpty()) {
            List<Step> longerSteps = new ArrayList<Step>();
            for (Step step : getHeatSteps(field, stepList)) {
                Field fieldForStep = fieldUtil.copy(field);
                Step stepForStep = fieldUtil.copy(step);
                longerSteps.addAll(makeStepLonger(fieldForStep, stepForStep));
            }
            stepList.addAll(longerSteps);
        }
        return stepList;
    }

    private boolean isBeating(Field field, Step step) {
        Field fieldLocal = fieldUtil.copy(field);
        Check checkLocal = fieldUtil.copy(step.getCheck());
        for(Position position : step.getPositionAfterMove()){
            if(!checkersRulesHolder.canBeat(fieldLocal, checkLocal, position))
                return false;
        }
        return true;
    }

    private List<Step> makeStepLonger(Field field, Step step) {
        Field fieldLocal = fieldUtil.copy(field);
        Check checkLocal = fieldUtil.copy(step.getCheck());
        step(fieldLocal, checkLocal, step.getPositionAfterMove());
        List<Step> stepList = validHeats(fieldLocal, checkLocal);
        List<Step> stepListFinal = new ArrayList<Step>();
        for (Step stepFinal : stepList) {
            stepListFinal.add(concat(step, stepFinal));
        }
        return stepListFinal;
    }

    private Step concat(Step step, Step stepFinal) {
        List<Position> positions = fieldUtil.copy(step.getPositionAfterMove());
        positions.addAll(fieldUtil.copy(stepFinal.getPositionAfterMove()));
        return new Step(step.getCheck(), (ArrayList<Position>) positions);
    }

    private void step(Field field, Check check, ArrayList<Position> positionAfterMove) {
        for(Position position : positionAfterMove){
            checkersRulesHolder.step(field, check, position);
        }
    }

    private List<Position> validatePositions(Field field, Check check, List<Position> positionList) {
        List<Position> positions = new ArrayList<Position>();
        boolean isHeat = false;
        for (Position position : positionList){
            if(isHeat){

            } else {
                if(checkersRulesHolder.canBeat(field, check, position)){
                    positions.clear();
                    isHeat = true;
                }
                if(checkersRulesHolder.calculateNextField(field, check, position, false))
                    positions.add(position);
            }
        }
        return positions;
    }

    private List<Position> collectPositions(Check check) {
        List<Position> positionList =new ArrayList<Position>();
        positionList.addAll(checkersRulesHolder.possibleHeatPositions(check.getPosition()));
        positionList.addAll(checkersRulesHolder.possibleSimpleStepPositions(check.getPosition()));
        return positionList;
    }

    public List<Step> getHeatSteps(Field field, List<Step> stepList) {
        List<Step> stepListRes = new ArrayList<Step>();
        for(Step step : stepList){
            if(checkersRulesHolder.canBeat(field, step.getCheck(), step.getPositionAfterMove().get(0)))
                stepListRes.add(step);
        }
        return stepListRes;
    }

    private List<Position> getHeatSteps(Field field, Check check, List<Position> positionList) {
        List<Position> positionsRes = new ArrayList<Position>();
        for(Position position : positionList){
            if(checkersRulesHolder.canBeat(field, check, position))
                positionsRes.add(position);
        }
        return positionsRes;
    }
}
