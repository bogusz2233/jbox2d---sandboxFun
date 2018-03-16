package sample;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.text.NumberFormat;


public class Main extends Application {

    private WorldCreator panel;
    private Thread gameLoop;


    @Override
    public void start(Stage primaryStage) throws Exception{


        panel = new WorldCreator();

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
            long delayTimeNano =16_000_000L;//(1L/70L * 1_000_000_000L);  // 20M  == 100 fps
            long timeFromLastUpdate =0;
            @Override
            public void handle(long currentNanoTime) {
                if(currentNanoTime - timeFromLastUpdate > delayTimeNano )
                {
                    frameCount++;
                    timeFromLastUpdate = currentNanoTime;
                    panel.update();

                    Runtime runtime = Runtime.getRuntime();
                    NumberFormat format = NumberFormat.getNumberInstance();
                    double allocatedMemory = runtime.totalMemory() - runtime.freeMemory();
                    double totalMem = runtime.totalMemory();
                    double freeMem = runtime.freeMemory();

                    allocatedMemory /= 1024 * 1024;
                    totalMem /= 1024 * 1024;
                    freeMem /= 1024 * 1024;

                    format.setMinimumFractionDigits(2);
                    format.setMaximumFractionDigits(2);
                    format.setMaximumIntegerDigits(4);
                    format.setMinimumIntegerDigits(3);
                    format.format(allocatedMemory);

                    String totalMemory =        "Total memory:        " + format.format(totalMem) +" MB";
                    String freeMemory =         "Freememory:          " + format.format(freeMem) +" MB";
                    String statisticString =    "Allocated memory: " + format.format(allocatedMemory) +" MB"
                            + "\n" + totalMemory + "\n" + freeMemory;
                    panel.draw("FPS: " + fps,statisticString);
                }
                if (currentNanoTime - frameUpdateTime > 1000_000_000){
                    fps =frameCount;
                    frameCount =0;
                    frameUpdateTime = currentNanoTime;
                }

            }
        }.start();
    }

    private void printMemoryUsing(){

    }

    public static void main(String[] args) {
        launch(args);
    }

}
