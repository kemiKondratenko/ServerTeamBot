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
        HashMap<FieldNode, Integer> min_3;
        HashMap<FieldNode, Integer> max_2 = new HashMap<FieldNode, Integer>();
        for (FieldNode n : nodes) {
            min_3 = new HashMap<FieldNode, Integer>();

            for (FieldNode n1 : n.getOponentsFields()) {
                int m2 = 12;
                for (FieldNode n2 : n1.getOponentsFields()) {
                    if(m2 > n2.getRaiting())
                        m2 = n2.getRaiting();
                }
                min_3.put(n1, m2);
            }

            int m1 = -12;
            for(FieldNode node: min_3.keySet()){
                if(m1 < node.getRaiting())
                    m1 = node.getRaiting();
            }
            max_2.put(n, m1);
        }
        int m = 12;
        FieldNode result = null;
        for(FieldNode node: max_2.keySet()){
            if(m > node.getRaiting()) {
                m = node.getRaiting();
                result = node;
            }
        }
        return result.getRoot();
    }
}
