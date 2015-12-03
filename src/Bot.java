import com.checkers.client.CheckersBot;
import com.checkers.domain.vo.Check;
import com.checkers.domain.vo.Field;
import com.checkers.domain.vo.Position;
import com.checkers.domain.vo.Step;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Eugene on 08.11.2015.
 */
public class Bot implements CheckersBot {

    @Override
    public Step calculateNextStep(Field field) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Integer x_check = null;
            Integer y_check = null;
            Check check = null;
            while (check == null){
                System.out.println("give me Check x y");
                x_check = Integer.valueOf(br.readLine());
                y_check = Integer.valueOf(br.readLine());
                check = getCheckByPosition(field, new Position(x_check, y_check));
                if(check.getColor() == 1){
                    System.out.println("not yours");
                    check = null;
                }
            }
            Position position = null;
            while (position == null){
                System.out.println("give me new x y");
                x_check = Integer.valueOf(br.readLine());
                y_check = Integer.valueOf(br.readLine());
                position = new Position(x_check, y_check);
            }
            ArrayList<Position> positions = new ArrayList<Position>();
            positions.add(position);
            return new Step(check, positions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Check getCheckByPosition(Field currentField, final Position enemyPosition){
        Check enemy = null;
        for(Check position: currentField.getAllChecks()){
            if(position.getPosition().equals(enemyPosition))
                return position;
        }
        return enemy;
    }
}