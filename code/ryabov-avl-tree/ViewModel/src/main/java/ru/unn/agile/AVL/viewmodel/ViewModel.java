package ru.unn.agile.AVL.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.AVL.model.AVLTree;
import ru.unn.agile.AVL.model.Node;

public class ViewModel {
    private final StringProperty key = new SimpleStringProperty();
    private final StringProperty value = new SimpleStringProperty();

    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<Operation>> operations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Operation.values()));

    private final BooleanProperty actionDisabled = new SimpleBooleanProperty();
    private final BooleanProperty valueDisabled = new SimpleBooleanProperty();

    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    private final AVLTree<String, Integer> tree = new AVLTree<>();

    public ViewModel() {
        clearInputFields();
        result.set("");
        status.set("");

        BooleanBinding actionIsAvailable = new BooleanBinding() {
            {
                super.bind(key, value);
            }

            @Override
            protected boolean computeValue() {
                if (operation.get() == Operation.INSERT) {
                    return status.get().equals(Status.READY_FOR_INSERT.toString());
                } else {
                    return status.get().equals(Status.READY_FOR_SEARCH.toString());
                }
            }
        };
        actionDisabled.bind(actionIsAvailable.not());

        BooleanBinding valueShouldNotBeEditable = new BooleanBinding() {
            {
                super.bind(operation);
            }

            @Override
            protected boolean computeValue() {
                return operation.get() == Operation.SEARCH;
            }
        };
        valueDisabled.bind(valueShouldNotBeEditable);

        final OperationChangeListener operationListener = new OperationChangeListener();
        operation.addListener(operationListener);

        final InputChangeListener keyListener = new InputChangeListener();
        key.addListener(keyListener);

        final InputChangeListener valueListener = new InputChangeListener();
        value.addListener(valueListener);

        operation.set(Operation.INSERT);
    }

    public void doAction() {
        if (operation.get() == Operation.SEARCH) {
            doSearchAction();
        } else {
            doInsertAction();
        }
    }

    private void doSearchAction() {
        if (actionDisabled.get()) {
            return;
        }

        Node<String, Integer> node = tree.find(key.get());
        if (node == null) {
            status.set(Status.NOT_FOUND.toString());
            result.set("");
        } else {
            status.set(Status.FOUND.toString());
            result.set(node.getValue().toString());
        }
    }

    private void doInsertAction() {
        if (actionDisabled.get()) {
            return;
        }

        tree.insert(key.get(), Integer.parseInt(value.get()));
        status.set(Status.INSERTED.toString());
        result.set("");
    }

    public StringProperty keyProperty() {
        return key;
    }

    public StringProperty valueProperty() {
        return value;
    }

    public ObjectProperty<Operation> operationProperty() {
        return operation;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public BooleanProperty actionDisabledProperty() {
        return actionDisabled;
    }

    public StringProperty resultProperty() {
        return result;
    }

    public BooleanProperty valueDisabledProperty() {
        return valueDisabled;
    }

    public final ObservableList<Operation> getOperations() {
        return operations.get();
    }

    public final String getStatus() {
        return status.get();
    }

    public String getResult() {
        return result.get();
    }

    public boolean isActionDisabled() {
        return actionDisabled.get();
    }

    public boolean isValueDisabled() {
        return valueDisabled.get();
    }


    private class InputChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (value.get() == null || key.get() == null) {
                return;
            }

            result.set("");
            if (operation.get() == Operation.INSERT) {
                if (!value.get().isEmpty()) {
                    try {
                        Integer.parseInt(value.get());
                    } catch (NumberFormatException nfe) {
                        status.set(Status.BAD_FORMAT.toString());
                        return;
                    }
                }

                if (!key.get().isEmpty() && !value.get().isEmpty()) {
                    status.set(Status.READY_FOR_INSERT.toString());
                } else {
                    status.set(Status.WAITING_FOR_INSERT.toString());
                }
            } else {
                if (!key.get().isEmpty()) {
                    status.set(Status.READY_FOR_SEARCH.toString());
                } else {
                    status.set(Status.WAITING_FOR_SEARCH.toString());
                }
            }
        }
    }

    private class OperationChangeListener implements ChangeListener<Operation> {
        @Override
        public void changed(final ObservableValue<? extends Operation> observable,
                            final Operation oldValue, final Operation newValue) {
            if (Operation.INSERT == newValue) {
                status.set(Status.WAITING_FOR_INSERT.toString());
                clearInputFields();
            } else {
                status.set(Status.WAITING_FOR_SEARCH.toString());
                clearInputFields();
            }
        }
    }

    private void clearInputFields() {
        key.set("");
        value.set("");
    }
}

enum Status {
    WAITING_FOR_SEARCH("Please enter a key name to search"),
    WAITING_FOR_INSERT("Please enter a key and value pair to insert"),
    READY_FOR_SEARCH("Press 'Do' or Enter"),
    READY_FOR_INSERT("Press 'Do' or Enter"),
    BAD_FORMAT("Value should be an integer"),
    INSERTED("Key-Value pair was inserted"),
    FOUND("Corresponding value was found"),
    NOT_FOUND("There is no such key in a tree");

    private final String name;

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
