package com.example.test;

import Exceptions.ADTException;
import View.ExitCommand;
import View.RunExample;
import View.TextMenu;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;


import Controller.Controller;
import Exceptions.MyException;
import Repository.IRepo;
import Repository.Repo;
import javafx.util.Callback;
import models.ADT.*;
import models.Expressions.*;
import models.PrgState;
import models.Statments.*;
import models.Types.BoolType;
import models.Types.IntType;
import models.Types.RefType;
import models.Types.StringType;
import models.Values.BoolValue;
import models.Values.IntValue;
import models.Values.StringValue;
import models.Values.Value;


class DataObject{
    String vName;

    String vValue;

    public DataObject(String vName, String vValue) {
        this.vName = vName;
        this.vValue = vValue;
    }

    public String getvName() {
        return vName;
    }

    public String getvValue() {
        return vValue;
    }

}


public class HelloApplication extends Application {
    Stage mainStage;

    Controller ctr = null;

    int prgIdSelected = 0;

    ObservableList<String> idItem = FXCollections.observableArrayList(
            "1", "2", "3");


    ObservableList<String> outItem = FXCollections.observableArrayList(
            "1", "2", "222");


    ObservableList<String> fileItem = FXCollections.observableArrayList(
            "file1", "f1", "f3");


    ObservableList<String> exeStackItem = FXCollections.observableArrayList(
            "asdf", "asdfdsfa", "agdsgr");

    ObservableList<DataObject> symTableItems = FXCollections.observableArrayList (
            new DataObject("ad", "afd"),
            new DataObject("ad1", "afd2"));

    ObservableList<DataObject> heapTableItems = FXCollections.observableArrayList (
            new DataObject("ad", "afd"),
            new DataObject("ad1", "afd2"));

    ObservableList<DataObject> latchTableItems = FXCollections.observableArrayList (
            new DataObject("ad", "afd"),
            new DataObject("ad1", "afd2"));

    TextField nrOfPrgStates = new TextField();

    void setCtr(Controller controller){
        ctr = controller;
    }

    void updateIdItem(){
        idItem.clear();
        idItem.addAll(ctr.getIds());

        if (!idItem.contains(String.valueOf(prgIdSelected))) prgIdSelected = Integer.parseInt(idItem.get(0));

        nrOfPrgStates.setText("Nr of prg states: "  + idItem.size());
    }

    void updateOutItem(){
        outItem.setAll(ctr.getOutItems());
    }

    void updateFileTable(){
        fileItem.setAll(ctr.getFileTableValues());
    }

    void updateSymbTable(){
        MyDictionary<String, Value> tmpSym = ctr.getSymTable(prgIdSelected);
        symTableItems.clear();
        Enumeration<String> e = tmpSym.getKeys();
        while(e.hasMoreElements()) {
            String k = e.nextElement();
            try {
                symTableItems.add(new DataObject(k, tmpSym.lookup(k).toString()));
            } catch (ADTException ex) {
                System.out.println(ex);
            }
        }

    }

    void updateHeapTable(){
        Map<Integer, Value> tmpHeap = ctr.getHeapTable(prgIdSelected);
        heapTableItems.clear();
        for (Integer key : tmpHeap.keySet()){
            heapTableItems.add(new DataObject(key.toString(), tmpHeap.get(key).toString()));
        }

    }

    void updateLatchTable(){
        Map<Integer, Integer> tmpLatch = ctr.getLatchTable();
        latchTableItems.clear();
        for (Integer key : tmpLatch.keySet()){
            latchTableItems.add(new DataObject(key.toString(), tmpLatch.get(key).toString()));
        }

    }


    void updateExeStack(){
        exeStackItem.setAll(ctr.getExeStackItem(prgIdSelected));
    }

    public void updateValues(){
        if (ctr.getIds().size() != 0){
            updateIdItem();
            updateOutItem();
            updateFileTable();
            updateExeStack();
            updateSymbTable();
            updateHeapTable();
            updateLatchTable();
        }else mainStage.show();
    }



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();



        MyIStack<IStmt> stk = new MyStack<IStmt>();
        MyIDictionary<String, Value> dic = new MyDictionary<String, Value>();
        MyIList<Value> list = new MyList<Value>();
        MyIHeap<Integer, Value> heap = new MyHeap<Integer, Value>();
        MyILatchTable<Integer, Integer> latchTable = new MyLatchTable<>();


        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));

        Controller ctr1 = null;
        try {
            ex1.typeCheck(new MyDictionary<>());
            PrgState prgState = new PrgState(stk, dic, list, ex1, new MyDictionary<>(), heap, latchTable);
            IRepo repo1 = new Repo(prgState, "C:\\Users\\dance\\IdeaProjects\\a2\\log1.txt");
            ctr1 = new Controller(repo1);
            System.out.println("Ex1 passed the type check and prg state has been created");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }



        IStmt ex2 = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1,new ValueExp(new IntValue(2)),new
                                ArithExp(3,new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp(1,new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new CompStmt(new PrintStmt(new VarExp("b")),
                                        new CompStmt(new NopStmt(), new NopStmt()))))));

        Controller ctr2 = null;
        try {
            ex2.typeCheck(new MyDictionary<>());
            PrgState prgState2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex2, new MyDictionary<>(), new MyHeap<>(), new MyLatchTable<>());
            IRepo repo2 = new Repo(prgState2, "log2.txt");
            ctr2 = new Controller(repo2);

            System.out.println("Ex2 passed the type check and prg state has been created");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }



        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        Controller ctr3 = null;

        try {
            ex3.typeCheck(new MyDictionary<>());
            PrgState prgState3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex3, new MyDictionary<>(), new MyHeap<>(), new MyLatchTable<>());
            IRepo repo3 = new Repo(prgState3, "log3.txt");
            ctr3 = new Controller(repo3);
            System.out.println("Ex3 passed the type check and prg state has been created");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStmt ex4 = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(4))),
                                new CompStmt(new IfStmt(new RelationalExp(new VarExp("a"), new VarExp("v"), ">"), new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        Controller ctr4 = null;

        try {
            ex4.typeCheck(new MyDictionary<>());
            PrgState prgState4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex4, new MyDictionary<>(), new MyHeap<>(), new MyLatchTable<>());
            IRepo repo4 = new Repo(prgState4, "log4.txt");
            ctr4 = new Controller(repo4);
            System.out.println("Ex4 passed the type check and prg state has been created");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        IStmt ex5 = new CompStmt(new VarDeclStmt("varf",new StringType()),
                new CompStmt(new VarDeclStmt("varc", new IntType()),
                        new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                                new CompStmt(new openRFile(new VarExp("varf")),
                                        new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new readFile(new VarExp("varf"),"varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new closeRFile(new VarExp("varf"))))))))));

        Controller ctr5 = null;

        try {
            ex5.typeCheck(new MyDictionary<>());
            PrgState prgState5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex5, new MyDictionary<>(), new MyHeap<>(), new MyLatchTable<>());
            IRepo repo5 = new Repo(prgState5, "log5.txt");
            ctr5 = new Controller(repo5);
            System.out.println("Ex5 passed the type check and prg state has been created");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        IStmt ex6 = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new rW("v", new ValueExp(new IntValue(30))), new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(new HeapAllocStmt("a", new VarExp("v")),
                                                new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(40))),
                                                        new CompStmt(new PrintStmt(new rH(new rH(new VarExp("a")))),
                                                                new CompStmt(new HeapAllocStmt("a", new VarExp("v")),
                                                                        new PrintStmt(new rH(new rH(new VarExp("a")))))))))))));

        Controller ctr6 = null;

        try {
            ex6.typeCheck(new MyDictionary<>());
            PrgState prgState6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex6, new MyDictionary<>(), new MyHeap<>(), new MyLatchTable<>());
            IRepo repo6 = new Repo(prgState6, "log6.txt");
            ctr6 = new Controller(repo6);
            System.out.println("Ex6 passed the type check and prg state has been created");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        IStmt ex7 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(5))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        Controller ctr7 = null;

        try {
            ex7.typeCheck(new MyDictionary<>());
            PrgState prgState7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex7, new MyDictionary<>(), new MyHeap<>(), new MyLatchTable<>());
            IRepo repo7 = new Repo(prgState7, "log7.txt");
            ctr7 = new Controller(repo7);
            System.out.println("Ex7 passed the type check and prg state has been created");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        IStmt ex8 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(5))),
                        new CompStmt(new VarDeclStmt("a" , new RefType(new IntType())),
                                new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new forkStmt(new CompStmt(new rW("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a")))))))));

        Controller ctr8 = null;
        try {
            ex8.typeCheck(new MyDictionary<>());

            PrgState prgState8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex8, new MyDictionary<>(), new MyHeap<>(), new MyLatchTable<>());
            IRepo repo8 = new Repo(prgState8, "log8.txt");
            ctr8 = new Controller(repo8);
            System.out.println("Ex8 passed the type check and prg state has been created");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStmt ex9 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new HeapAllocStmt("v1", new ValueExp(new IntValue(2))),
                        new CompStmt(new VarDeclStmt("v2" , new RefType(new IntType())),
                                new CompStmt(new HeapAllocStmt("v2", new ValueExp(new IntValue(3))),
                                        new CompStmt(new VarDeclStmt("v3" , new RefType(new IntType())),
                                                new CompStmt(new HeapAllocStmt("v3", new ValueExp(new IntValue(4))),
                                                        new CompStmt(new VarDeclStmt("cnt", new IntType()),
                                                                new CompStmt(new NewLatch("cnt", new rH(new VarExp("v2"))),
                                                                        new CompStmt(new forkStmt(
                                                                                new CompStmt(new rW("v1", new ArithExp(3, new rH(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                        new CompStmt(new PrintStmt(new rH(new VarExp("v1"))),
                                                                                                new CompStmt(new countDownStmt("cnt"), new forkStmt(new CompStmt(new rW("v2", new ArithExp(3, new rH(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                                        new CompStmt(new PrintStmt(new rH(new VarExp("v2"))),
                                                                                                                                new CompStmt(new countDownStmt("cnt"), new forkStmt(new CompStmt(new rW("v3", new ArithExp(3, new rH(new VarExp("v3")), new ValueExp(new IntValue(10)))),
                                                                                                                                                        new CompStmt(new PrintStmt(new rH(new VarExp("v3"))), new countDownStmt("cnt")))))))))))),
                                                                                new CompStmt(new Await("cnt"),
                                                                                        new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                                new CompStmt(new countDownStmt("cnt"), new PrintStmt(new ValueExp(new IntValue(100))) ))))))))))));

        Controller ctr9 = null;
        try {
            ex8.typeCheck(new MyDictionary<>());

            PrgState prgState9 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex9, new MyDictionary<>(), new MyHeap<>(), new MyLatchTable<>());
            IRepo repo9 = new Repo(prgState9, "log9.txt");
            ctr9 = new Controller(repo9);
            System.out.println("Ex9 passed the type check and prg state has been created");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStmt ex10 = new CompStmt(new VarDeclStmt("v",new IntType()),
                        new CompStmt(new VarDeclStmt("a" , new RefType(new IntType())),
                                new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(0))),
                                        new CompStmt(new VarDeclStmt("b" , new RefType(new IntType())),
                                                new CompStmt(new HeapAllocStmt("b", new ValueExp(new IntValue(0))),
                                                    new CompStmt(new rW("a", new ValueExp(new IntValue(1))),
                                                            new CompStmt(new rW("b", new ValueExp(new IntValue(2))),
                                                                    new CompStmt(new ConditionalAssignment(new RelationalExp(new rH(new VarExp("a")), new rH(new VarExp("b")), "<"),
                                                                            new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200)), "v"),
                                                                               new CompStmt(new PrintStmt(new VarExp("v")),
                                                                                       new CompStmt(new ConditionalAssignment(new RelationalExp(new ArithExp(2, new rH(new VarExp("b")), new ValueExp(new IntValue(2))), new rH(new VarExp("a")), ">"),
                                                                                               new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200)), "v"), new PrintStmt(new VarExp("v"))))))))))));

        Controller ctr10 = null;
        try {
            ex10.typeCheck(new MyDictionary<>());

            PrgState prgState10 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex10, new MyDictionary<>(), new MyHeap<>(), new MyLatchTable<>());
            IRepo repo10 = new Repo(prgState10, "log10.txt");
            ctr10 = new Controller(repo10);
            System.out.println("Ex10 passed the type check and prg state has been created");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }



        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
        menu.addCommand(new RunExample("10", ex10.toString(), ctr10));
        //menu.show();






        ListView<String> listViewOfPrgStates = new ListView<String>();
        ObservableList<String> listPrgStates = FXCollections.observableArrayList (
                ex1.toString(), ex2.toString(), ex3.toString(), ex4.toString(),
                ex5.toString(), ex6.toString(), ex7.toString(), ex8.toString(), ex9.toString(), ex10.toString());
        listViewOfPrgStates.setItems(listPrgStates);

        StackPane root = new StackPane();
        root.getChildren().add(listViewOfPrgStates);

        Controller finalCtr = ctr1;
        Controller finalCtr1 = ctr2;
        Controller finalCtr2 = ctr3;
        Controller finalCtr3 = ctr4;
        Controller finalCtr4 = ctr5;
        Controller finalCtr5 = ctr6;
        Controller finalCtr6 = ctr8;
        Controller finalCtr7 = ctr7;
        Controller finalCtr8 = ctr9;
        Controller finalCtr9 = ctr10;
        listViewOfPrgStates.setOnMouseClicked(e->{
            if (listViewOfPrgStates.getSelectionModel().getSelectedIndex() == 0) {setCtr(finalCtr);System.out.println("yes");};
            if (listViewOfPrgStates.getSelectionModel().getSelectedIndex() == 1) setCtr(finalCtr1);
            if (listViewOfPrgStates.getSelectionModel().getSelectedIndex() == 2) setCtr(finalCtr2);
            if (listViewOfPrgStates.getSelectionModel().getSelectedIndex() == 3) setCtr(finalCtr3);
            if (listViewOfPrgStates.getSelectionModel().getSelectedIndex() == 4) setCtr(finalCtr4);
            if (listViewOfPrgStates.getSelectionModel().getSelectedIndex() == 5) setCtr(finalCtr5);
            if (listViewOfPrgStates.getSelectionModel().getSelectedIndex() == 6) setCtr(finalCtr7);
            if (listViewOfPrgStates.getSelectionModel().getSelectedIndex() == 7) setCtr(finalCtr6);
            if (listViewOfPrgStates.getSelectionModel().getSelectedIndex() == 8) setCtr(finalCtr8);
            if (listViewOfPrgStates.getSelectionModel().getSelectedIndex() == 9) setCtr(finalCtr9);
            System.out.println(listViewOfPrgStates.getSelectionModel().getSelectedItem());
            ctr.setUpExecutor();
            prgIdSelected = ctr.getPrgList().get(0).getId();
            mainStage.hide();
            updateValues();
        });
        mainStage = new Stage();
        mainStage.setScene(new Scene(root, 700, 300));
        mainStage.show();


        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(4, new Label("Second window"))));
        secondStage.show();

        //verticalBox for out, file and prgStates view
        VBox verticalBox = new VBox();

        //vBox with PrgStates ids
        VBox vBoxForPrgStates = new VBox();

        //horizontal box for NrOfPrgStates and (out, file and prgStates view)
        HBox horizontalBox = new HBox();

        //Nr of PrgStates
        nrOfPrgStates.setText("Nr of PrgStates: 0");

        ListView<String> idView = new ListView<>();
        idView.setOnMouseClicked(e->{
            prgIdSelected = Integer.parseInt(idView.getSelectionModel().getSelectedItem());
            System.out.println(prgIdSelected);
            updateValues();
        });

        idView.setItems(idItem);

        //outView
        ListView<String> outView = new ListView<>();
        outView.setItems(outItem);

        //fileView
        ListView<String> fileView = new ListView<>();
        fileView.setItems(fileItem);

        //PrgStatesView
        ListView<String> prgStatesView = new ListView<>();
        ObservableList<String> prgStatesItem = FXCollections.observableArrayList(
                "1", "2", "3");
        prgStatesView.setItems(prgStatesItem);

        //exeStackView
        ListView<String> exeStackView = new ListView<>();
        exeStackView.setItems(exeStackItem);

        final Label exeStackLabel = new Label("Exe stack");

        VBox exeStackBox = new VBox();
        exeStackBox.getChildren().addAll(exeStackLabel, exeStackView);


        //symTableView
        TableView<DataObject> symTableView = new TableView<>();
        TableColumn<DataObject, String> varNameColumn = new TableColumn<>("Var Name");
        TableColumn<DataObject, String> symValueColumn = new TableColumn<>("Value");
        varNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataObject, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataObject, String> rowObject)
            {
                return new ReadOnlyStringWrapper(rowObject.getValue().getvName());
                // or whatever else your method will be called
            }
        });
        symValueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataObject, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataObject, String> rowObject)
            {
                return new ReadOnlyStringWrapper(rowObject.getValue().getvValue());
                // or whatever else your method will be called
            }
        });

        final Label symTableLabel = new Label("Sym Table");

        symTableView.getColumns().addAll(varNameColumn, symValueColumn);

        symTableView.setItems(symTableItems);

        VBox symBox = new VBox();
        symBox.getChildren().addAll(symTableLabel, symTableView);


        //heapTable
        //heapTableView
        TableView<DataObject> heapTableView = new TableView<>();

        TableColumn<DataObject, String> addressColumn = new TableColumn<>("Address");
        TableColumn<DataObject, String> valueColumn = new TableColumn<>("Value");

        addressColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataObject, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataObject, String> rowObject)
            {
                return new ReadOnlyStringWrapper(rowObject.getValue().getvName());
            }
        });
        valueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataObject, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataObject, String> rowObject)
            {
                return new ReadOnlyStringWrapper(rowObject.getValue().getvValue());
            }
        });

        heapTableView.getColumns().addAll(addressColumn, valueColumn);


        heapTableView.setItems(heapTableItems);

        final Label heapTable = new Label("Heap Table");




        TableView<DataObject> latchTableView = new TableView<>();

        TableColumn<DataObject, String> locationColumn = new TableColumn<>("Location");
        TableColumn<DataObject, String> valueLColumn = new TableColumn<>("Value");

        locationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataObject, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataObject, String> rowObject)
            {
                return new ReadOnlyStringWrapper(rowObject.getValue().getvName());
            }
        });
        valueLColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataObject, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataObject, String> rowObject)
            {
                return new ReadOnlyStringWrapper(rowObject.getValue().getvValue());
            }
        });

        latchTableView.getColumns().addAll(locationColumn, valueLColumn);


        latchTableView.setItems(latchTableItems);

        final Label latchTableLabel = new Label("Heap Table");




        VBox heapBox = new VBox();
        heapBox.getChildren().addAll(heapTable, heapTableView, symBox);

        //setBoxes
        verticalBox.getChildren().addAll(outView, fileView);

        vBoxForPrgStates.getChildren().addAll(nrOfPrgStates, idView);


        Button oneStepButton = new Button("One step for all programs");
        oneStepButton.setOnAction(e->{
            ctr.setUpOneStep();
            updateValues();
        });

        horizontalBox.getChildren().addAll(vBoxForPrgStates, verticalBox, heapBox, exeStackBox, latchTableView, oneStepButton);

        StackPane secondRoot = new StackPane();
        secondRoot.getChildren().addAll(horizontalBox);
        secondStage.setScene(new Scene(secondRoot, 800, 300));







    }

    public static void main(String[] args) {
        launch();
    }
}