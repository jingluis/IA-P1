import aima.search.framework.GoalTest;

public class ServerGoalTest implements GoalTest {
    @Override
    public boolean isGoalState(Object aState) {
        ServerState state = (ServerState) aState;
        return state.isGoalState();
    }
}
