package rating;

import com.checkers.domain.vo.Check;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Step;
import steps.CheckersRulesHolder;
import steps.StepCalculator;
import utils.FieldUtil;

import java.util.*;

/**
 * Created by KutsykV on 04.12.2015.
 */
public class FieldTree {

    CheckersRulesHolder checkersRulesHolder;
    StepCalculator stepCalculator;
    FieldUtil fieldUtil;
    private List<FieldNode> nodes;

    public FieldTree(Field root) {
        fieldUtil = new FieldUtil();
        checkersRulesHolder = new CheckersRulesHolder();
        stepCalculator = new StepCalculator(checkersRulesHolder, fieldUtil);
        nodes = new ArrayList<FieldNode>();
        nodes.add(new FieldNode(root, -1));
    }

    public Field getBestField() {
        for (FieldNode n : nodes) {
            for (FieldNode n1 : n.getOponentsFields()) {
                for (FieldNode n2 : n1.getOponentsFields()) {
                }
            }
        }
        return null;
    }
}
