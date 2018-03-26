package SmartExp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import static org.parboiled.errors.ErrorUtils.printParseErrors;

public class View extends Application {
    private Stage rootStage;
    private Scene mainScene;

    public void start(Stage primaryStage) throws Exception {
        rootStage = primaryStage;
        primaryStage.setHeight(750);
        primaryStage.setWidth(500);
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(500);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(
                getClass().getClassLoader().getResource("fxml/main.fxml")
        );
        Parent content = loader.load();
        mainScene = new Scene(content);
        rootStage.setScene(mainScene);
        addButtons();
        rootStage.show();
    }
    int leftBracketQuantity() {
        TextArea textArea = (TextArea)
                mainScene.lookup("#MainFrame");
        int k = textArea.getText().length();
        int quantity = 0;

        for (int i = 0; i < k; i++) {
            char c = textArea.getText().charAt(i);
            String charToString = Character.toString(c);
            if (charToString.equals("(")) {
                quantity = quantity + 1;

            }
        }
        return quantity;
    }

    int rightBracketQuantity() {
        TextArea textArea = (TextArea)
                mainScene.lookup("#MainFrame");
        int k = textArea.getText().length();
        int quantity = 0;

        for (int i = 0; i < k; i++) {
            char c = textArea.getText().charAt(i);
            String charToString = Character.toString(c);
            if (charToString.equals(")")) {
                quantity = quantity + 1;

            }
        }
        return quantity;
    }

    public void open() {
        Application.launch(getClass());
    }

    private void addButtons() {
        TextArea textArea = (TextArea)
                mainScene.lookup("#MainFrame");

        GridPane gridPane = (GridPane)
                mainScene.lookup("#Buttons");

        textArea.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                textArea.setScrollTop(Double.MAX_VALUE);
            }
    });
    Integer rows[] = {3, 3, 3, 2, 2, 2, 1, 1, 1, 4, 3, 2, 2, 3, 0, 0, 1, 4, 0, 0, 4, 0, 1, 4};
    String lettersForNumbers[] = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "@", "^", "-", "+", "(", ")", "√", "ABC",
            "С", "←", ".", "÷", "×", "="};
    Integer columns[] = {6, 12, 18, 6, 12, 18, 6, 12, 18, 12, 0, 0, 24, 24, 0, 6, 0, 0, 12, 18, 18, 24, 24, 24};
    Button numbers[] = new Button[24];
    int i;
        for (i = 0; i < numbers.length; i++) {
            numbers[i] = new Button(lettersForNumbers[i]);
            gridPane.add(numbers[i], columns[i], rows[i], 6, 1);
            numbers[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }
        textArea.setEditable(false);

        numbers[14].setOnAction(event -> {
            Platform.runLater(() -> {
                if (textArea.getText().endsWith("1")
                        || (textArea.getText().endsWith("2"))
                        || (textArea.getText().endsWith("3"))
                        || (textArea.getText().endsWith("4"))
                        || (textArea.getText().endsWith("5"))
                        || (textArea.getText().endsWith("6"))
                        || (textArea.getText().endsWith("7"))
                        || (textArea.getText().endsWith("8"))
                        || (textArea.getText().endsWith("9"))
                        || (textArea.getText().endsWith("0"))
                        || (textArea.getText().endsWith("."))
                        || (textArea.getText().endsWith(")"))) {
                } else textArea.setText(textArea.getText() + "(");

            });
        });

        numbers[16].setOnAction(event -> {
            Platform.runLater(() -> {
                if (textArea.getText().endsWith("1")
                        || (textArea.getText().endsWith("2"))
                        || (textArea.getText().endsWith("3"))
                        || (textArea.getText().endsWith("4"))
                        || (textArea.getText().endsWith("5"))
                        || (textArea.getText().endsWith("6"))
                        || (textArea.getText().endsWith("7"))
                        || (textArea.getText().endsWith("8"))
                        || (textArea.getText().endsWith("9"))
                        || (textArea.getText().endsWith("0"))
                        || (textArea.getText().endsWith("."))
                        || (textArea.getText().endsWith(")"))
                        || (textArea.getText().endsWith("√"))) {
                } else textArea.setText(textArea.getText() + "√");

            });
        });

        numbers[11].setOnAction(event ->
                Platform.runLater(() -> {
                    if (textArea.getText().endsWith("+")
                            || (textArea.getText().endsWith("-"))
                            || (textArea.getText().endsWith("."))
                            || (textArea.getText().endsWith("×"))
                            || (textArea.getText().endsWith("÷"))
                            || (textArea.getText().endsWith("@"))
                            || (textArea.getText().endsWith("^"))
                            || (textArea.getText().endsWith("√"))
                            || (textArea.getText().endsWith("("))
                            || (textArea.getText().isEmpty()) == true) {
                    } else
                        textArea.setText(textArea.getText() + "^");

                }));

        numbers[10].setOnAction(event ->
                Platform.runLater(() -> {
                    if (textArea.getText().endsWith("+")
                            || (textArea.getText().endsWith("-"))
                            || (textArea.getText().endsWith("."))
                            || (textArea.getText().endsWith("×"))
                            || (textArea.getText().endsWith("÷"))
                            || (textArea.getText().endsWith("@"))
                            || (textArea.getText().endsWith("^"))
                            || (textArea.getText().endsWith("√"))
                            || (textArea.getText().endsWith("("))
                            || (textArea.getText().isEmpty()) == true) {
                    } else
                        textArea.setText(textArea.getText() + "@");

                }));

        numbers[17].setOnAction(event ->
                Platform.runLater(() -> {
                    gridPane.getChildren().clear();
                    addAlphabet();

                }));


        numbers[15].setOnAction(event -> {
            Platform.runLater(() -> {
                if (textArea.getText().endsWith("-")
                        || (textArea.getText().endsWith("."))
                        || (textArea.getText().endsWith("+"))
                        || (textArea.getText().endsWith("("))
                        || (textArea.getText().endsWith("÷"))
                        || (textArea.getText().endsWith("×"))
                        || (textArea.getText().endsWith("@"))
                        || (textArea.getText().endsWith("^"))
                        || (textArea.getText().endsWith("."))
                        || (textArea.getText().endsWith("."))
                        || (leftBracketQuantity() == rightBracketQuantity())
                        || (textArea.getText().isEmpty())) {
                } else textArea.setText(textArea.getText() + ")" );

            });
        });

        Button constants = new Button("const");
        gridPane.add(constants, 6, 4, 6, 1);
        constants.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        constants.styleProperty().setValue("-fx-font-size: 200%");
        constants.setOnAction(event -> {
            Platform.runLater(() -> {
                gridPane.getChildren().clear();
                addConst();

            });
        });


        numbers[18].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        numbers[18].setOnAction(event ->
                Platform.runLater(() -> {
                    textArea.setText("");
                }));

        numbers[19].setOnAction(event ->
                Platform.runLater(() -> {
                    String string = new String(textArea.getText());
                    textArea.setText(string.substring(0, string.length() - 1));

                }));

        numbers[20].setOnAction(event -> {
            Platform.runLater(() -> {
                if (textArea.getText().endsWith("+")
                        || (textArea.getText().endsWith("-"))
                        || (textArea.getText().endsWith("."))
                        || (textArea.getText().endsWith("×"))
                        || (textArea.getText().endsWith("÷"))
                        || (textArea.getText().endsWith("@"))
                        || (textArea.getText().endsWith("^"))
                        || (textArea.getText().endsWith("√"))
                        || (textArea.getText().endsWith("("))
                        || (textArea).getText().endsWith(")")
                        || (textArea.getText().isEmpty()) == true) {
                } else
                    textArea.setText(textArea.getText() + ".");
            });
        });


        numbers[12].setOnAction(event -> {
            Platform.runLater(() -> {
                if (textArea.getText().endsWith("+")
                        || (textArea.getText().endsWith("-"))
                        || (textArea.getText().endsWith("."))
                        || (textArea.getText().endsWith("×"))
                        || (textArea.getText().endsWith("÷"))
                        || (textArea.getText().endsWith("@"))
                        || (textArea.getText().endsWith("^"))
                        || (textArea.getText().endsWith("√"))
                        || (textArea.getText().isEmpty()) == true) {
                } else
                    textArea.setText(textArea.getText() + "-");
            });
        });


        numbers[23].setOnAction(event ->
                Platform.runLater(() -> {
                    if (textArea.getText().endsWith("+")
                            || (textArea.getText().endsWith("-"))
                            || (textArea.getText().endsWith("."))
                            || (textArea.getText().endsWith("×"))
                            || (textArea.getText().endsWith("÷"))
                            || (textArea.getText().endsWith("@"))
                            || (textArea.getText().endsWith("^"))
                            || (textArea.getText().endsWith("√"))
                            || (textArea.getText().isEmpty()) == true) {
                    } else {
                        CalculatorParser parser = Parboiled.createParser(CalculatorParser.class);
                        ParsingResult<?> result = new RecoveringParseRunner(parser.InputLine()).run(textArea.getText());

                        if (result.hasErrors()) {
                            System.out.println("\nParse Errors:\n" + printParseErrors(result));
                        }

                        Object value = result.parseTreeRoot.getValue();
                        if (value != null) {
                            textArea.setText(value.toString());
                        }
                    }
                }));

        numbers[16].styleProperty().setValue("-fx-font-size: 300%");
        numbers[19].styleProperty().setValue("-fx-font-size: 400%");
        numbers[20].styleProperty().setValue("-fx-font-size: 400%");
        numbers[13].styleProperty().setValue("-fx-font-size: 400%");
        numbers[23].styleProperty().setValue("-fx-font-size: 400%");
        numbers[21].styleProperty().setValue("-fx-font-size: 400%");
        numbers[22].styleProperty().setValue("-fx-font-size: 400%");
        numbers[12].styleProperty().setValue("-fx-font-size: 400%");

        for (Node node : gridPane.getChildren()) {
            if (((Button) node).getText() != "="
                    && (((Button) node).getText() != "ABC")
                    && (((Button) node).getText() != "С")
                    && (((Button) node).getText() != "←")
                    && (((Button) node).getText() != "x^y")
                    && (((Button) node).getText() != "x@y")
                    && (((Button) node).getText() != "const")
                    && (((Button) node).getText() != "+")
                    && (((Button) node).getText() != "÷")
                    && (((Button) node).getText() != "×")
                    && (((Button) node).getText() != "-")
                    && (((Button) node).getText() != ".")
                    && (((Button) node).getText() != ")")
                    && (((Button) node).getText() != "√")
                    && (((Button) node).getText() != "(")) {
                ((Button) node).setOnAction(event -> {
                    Platform.runLater(() -> {
                        if (textArea.getText().endsWith(")")) {
                        } else textArea.setText(textArea.getText() + ((Button) node).getText());

                    });
                });
            }
        }
        for (Node node : gridPane.getChildren()) {
            if (((Button) node).getText() != "1"
                    && (((Button) node).getText() != "2")
                    && (((Button) node).getText() != "3")
                    && (((Button) node).getText() != "4")
                    && (((Button) node).getText() != "5")
                    && (((Button) node).getText() != "6")
                    && (((Button) node).getText() != "7")
                    && (((Button) node).getText() != "8")
                    && (((Button) node).getText() != "9")
                    && (((Button) node).getText() != "0")
                    && (((Button) node).getText() != "С")
                    && (((Button) node).getText() != "←")
                    && (((Button) node).getText() != "ABC")
                    && (((Button) node).getText() != "const")
                    && (((Button) node).getText() != "С")
                    && (((Button) node).getText() != "=")
                    && (((Button) node).getText() != "С")
                    && (((Button) node).getText() != "x^y")
                    && (((Button) node).getText() != "x@y")
                    && (((Button) node).getText() != "(")
                    && (((Button) node).getText() != ")")
                    && (((Button) node).getText() != "√")
                    && (((Button) node).getText() != ".")
                    && (((Button) node).getText() != "-")) {
                ((Button) node).setOnAction(event -> {
                    Platform.runLater(() -> {
                        if (textArea.getText().endsWith("+")
                                || (textArea.getText().endsWith("-"))
                                || (textArea.getText().endsWith("."))
                                || (textArea.getText().endsWith("×"))
                                || (textArea.getText().endsWith("÷"))
                                || (textArea.getText().endsWith("@"))
                                || (textArea.getText().endsWith("^"))
                                || (textArea.getText().endsWith("√"))
                                || (textArea.getText().endsWith("("))
                                || (textArea.getText().isEmpty()) == true) {
                        } else
                            textArea.setText(textArea.getText() + ((Button) node).getText());
                    });
                });
            }
        }
    }
    private void addConst(){
        TextArea textArea = (TextArea)
                mainScene.lookup("#MainFrame");

        GridPane gridPane = (GridPane)
                mainScene.lookup("#Buttons");
        Button backToMain = new Button("←");
        gridPane.add(backToMain, 0, 4, 10, 1);
        backToMain.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        backToMain.styleProperty().setValue("-fx-font-size: 500%");
        backToMain.setOnAction(event ->
                Platform.runLater(() -> {
                    gridPane.getChildren().clear();
                    addButtons();

                }));

        Button pi = new Button("π");
        gridPane.add(pi,0,0,5,1);
        pi.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pi.styleProperty().setValue("-fx-font-size: 400%");
        pi.setOnAction(event -> {
            Platform.runLater(() -> {
                  textArea.setText(textArea.getText() + "π" );

            });
        });
    }
    private void addAlphabet() {
        TextArea textArea = (TextArea)
                mainScene.lookup("#MainFrame");

        GridPane gridPane = (GridPane)
                mainScene.lookup("#Buttons");

        gridPane.getChildren().clear();

        Integer rows[] = {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3};
        String letters[] = {
                "A", "B", "С", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X"
        };
        Button[] alphabet = new Button[25];
        int k, i;
        for (i = 0, k = 0; i < letters.length; i++, k += 5) {
            if (k == 30) {
                k = 0;
            }
            alphabet[i] = new Button(letters[i]);
            gridPane.add(alphabet[i], k, rows[i], 5, 1);
            alphabet[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }

        Button backToMain = new Button("←");
        gridPane.add(backToMain, 0, 4, 10, 1);
        backToMain.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        backToMain.styleProperty().setValue("-fx-font-size: 500%");
        backToMain.setOnAction(event ->
                Platform.runLater(() -> {
                    gridPane.getChildren().clear();
                    addButtons();

                }));

        Button letterY = new Button("Y");
        gridPane.add(letterY, 10, 4, 10, 1);
        letterY.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Button letterZ = new Button("Z");
        gridPane.add(letterZ, 20, 4, 10, 1);
        letterZ.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        for (Node node : gridPane.getChildren()) {
            if (((Button) node).getText() != "←"
                    ) {
                ((Button) node).setOnAction(event -> {
                    Platform.runLater(() -> {
                        textArea.setText(textArea.getText() + ((Button) node).getText());
                    });
                });
            }
        }
    }
}