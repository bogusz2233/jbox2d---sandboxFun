package sample;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    private GamePanel panel;
    private Thread gameLoop;


    @Override
    public void start(Stage primaryStage) throws Exception{


        panel = new GamePanel();

        primaryStage.setTitle("Box2D - TEST");
        primaryStage.setScene(panel.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
        gameLoopInit();

    }

    private void gameLoopInit(){
        new AnimationTimer() {
            int frameCount = 0;
            int fps =0;
            long frameUpdateTime=0;
            long delayTimeNano =(long)(1.0/60.0 * 1_000_000_000);  // 20M  == 100 fps
            long timeFromLastUpdate =0;
            @Override
            public void handle(long currentNanoTime) {
                if(currentNanoTime - timeFromLastUpdate > delayTimeNano )
                {
                    frameCount++;
                    timeFromLastUpdate = currentNanoTime;
                    panel.update();
                    panel.draw("FPS: " + fps);
                }
                if (currentNanoTime - frameUpdateTime > 1000_000_000){
                    fps =frameCount;
                    frameCount =0;
                    frameUpdateTime = currentNanoTime;
                }

            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
