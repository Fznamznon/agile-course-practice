package ru.unn.agile.AVL.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.AVL.infrastructure.FileLogger;
import ru.unn.agile.AVL.viewmodel.*;

import java.io.IOException;

import static java.lang.System.exit;

public class AVLTree {
    private static final Integer ERR_EXIT_CODE = 1;
    private static final String LOG_NAME = "ryabov-avl-tree-View.log";
    @FXML
    private ViewModel viewModel;

    @FXML
    private Button performAction;
    @FXML
    private ChoiceBox<Operation> actionType;
    @FXML
    private TextField key;
    @FXML
    private TextField value;
    @FXML
    private ListView<String> log;

    @FXML
    void initialize() {
        try {
            FileLogger logger = new FileLogger(LOG_NAME);
            viewModel.setLogger(logger);
        } catch (IOException e) {
            exit(ERR_EXIT_CODE);
        }

        // Two-way binding hasn't supported by FXML yet, so place it in code-behind
        actionType.valueProperty().bindBidirectional(viewModel.operationProperty());

        key.textProperty().bindBidirectional(viewModel.keyProperty());
        value.textProperty().bindBidirectional(viewModel.valueProperty());
        log.itemsProperty().bind(viewModel.logListProperty());

        performAction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.doAction();
            }
        });
    }
}
