package View;//package View;
//
//import Controller.Controller;
//import Exceptions.MyException;
//import Repository.IRepo;
//import Repository.Repo;
//import models.ADT.*;
//import models.Expressions.*;
//import models.PrgState;
//import models.Statments.*;
//import models.Types.BoolType;
//import models.Types.IntType;
//import models.Types.RefType;
//import models.Types.StringType;
//import models.Values.BoolValue;
//import models.Values.IntValue;
//import models.Values.StringValue;
//import models.Values.Value;
//
//public class Interpreter {
//    public static void main(String[] args) {
//        MyIStack<IStmt> stk = new MyStack<IStmt>();
//        MyIDictionary<String, Value> dic = new MyDictionary<String, Value>();
//        MyIList<Value> list = new MyList<Value>();
//        MyIHeap<Integer, Value> heap = new MyHeap<Integer, Value>();
//
//
//        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
//
//        Controller ctr1 = null;
//        try {
//            ex1.typeCheck(new MyDictionary<>());
//            PrgState prgState = new PrgState(stk, dic, list, ex1, new MyDictionary<>(), heap, );
//            IRepo repo1 = new Repo(prgState, "C:\\Users\\dance\\IdeaProjects\\a2\\log1.txt");
//            ctr1 = new Controller(repo1);
//            System.out.println("Ex1 passed the type check and prg state has been created");
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//
//
//
//        IStmt ex2 = new CompStmt(new VarDeclStmt("a",new IntType()),
//                new CompStmt(new VarDeclStmt("b",new IntType()),
//                        new CompStmt(new AssignStmt("a", new ArithExp(1,new ValueExp(new IntValue(2)),new
//                                ArithExp(3,new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
//                                new CompStmt(new AssignStmt("b",new ArithExp(1,new VarExp("a"), new ValueExp(new
//                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
//
//        Controller ctr2 = null;
//        try {
//            ex2.typeCheck(new MyDictionary<>());
//            PrgState prgState2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex2, new MyDictionary<>(), new MyHeap<>());
//            IRepo repo2 = new Repo(prgState2, "log2.txt");
//            ctr2 = new Controller(repo2);
//
//            System.out.println("Ex2 passed the type check and prg state has been created");
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//
//
//
//        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
//                new CompStmt(new VarDeclStmt("v", new IntType()),
//                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
//                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v",new ValueExp(new
//                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
//                                        new PrintStmt(new VarExp("v"))))));
//        Controller ctr3 = null;
//
//        try {
//            ex3.typeCheck(new MyDictionary<>());
//            PrgState prgState3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex3, new MyDictionary<>(), new MyHeap<>());
//            IRepo repo3 = new Repo(prgState3, "log3.txt");
//            ctr3 = new Controller(repo3);
//            System.out.println("Ex3 passed the type check and prg state has been created");
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//
//
//        IStmt ex4 = new CompStmt(new VarDeclStmt("a",new IntType()),
//                new CompStmt(new VarDeclStmt("v", new IntType()),
//                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(4))),
//                                new CompStmt(new IfStmt(new RelationalExp(new VarExp("a"), new VarExp("v"), ">"), new AssignStmt("v",new ValueExp(new
//                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
//                                        new PrintStmt(new VarExp("v"))))));
//        Controller ctr4 = null;
//
//        try {
//            ex4.typeCheck(new MyDictionary<>());
//            PrgState prgState4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex4, new MyDictionary<>(), new MyHeap<>());
//            IRepo repo4 = new Repo(prgState4, "log4.txt");
//            ctr4 = new Controller(repo4);
//            System.out.println("Ex4 passed the type check and prg state has been created");
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//
//        IStmt ex5 = new CompStmt(new VarDeclStmt("varf",new StringType()),
//                new CompStmt(new VarDeclStmt("varc", new IntType()),
//                        new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
//                                new CompStmt(new openRFile(new VarExp("varf")),
//                                        new CompStmt(new readFile(new VarExp("varf"), "varc"),
//                                                new CompStmt(new PrintStmt(new VarExp("varc")),
//                                                        new CompStmt(new readFile(new VarExp("varf"),"varc"),
//                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new closeRFile(new VarExp("varf"))))))))));
//
//        Controller ctr5 = null;
//
//        try {
//            ex5.typeCheck(new MyDictionary<>());
//            PrgState prgState5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex5, new MyDictionary<>(), new MyHeap<>());
//            IRepo repo5 = new Repo(prgState5, "log5.txt");
//            ctr5 = new Controller(repo5);
//            System.out.println("Ex5 passed the type check and prg state has been created");
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//
//        IStmt ex6 = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
//                        new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
//                                new CompStmt(new rW("v", new ValueExp(new IntValue(30))), new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
//                                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                                                new CompStmt(new HeapAllocStmt("a", new VarExp("v")),
//                                                        new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(40))),
//                                                                new CompStmt(new PrintStmt(new rH(new rH(new VarExp("a")))),
//                                                                        new CompStmt(new HeapAllocStmt("a", new VarExp("v")),
//                                                                                new PrintStmt(new rH(new rH(new VarExp("a")))))))))))));
//
//        Controller ctr6 = null;
//
//        try {
//            ex6.typeCheck(new MyDictionary<>());
//            PrgState prgState6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex6, new MyDictionary<>(), new MyHeap<>());
//            IRepo repo6 = new Repo(prgState6, "log6.txt");
//            ctr6 = new Controller(repo6);
//            System.out.println("Ex6 passed the type check and prg state has been created");
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//
//        IStmt ex7 = new CompStmt(new VarDeclStmt("v",new IntType()),
//                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(5))),
//                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
//                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))),
//                                            new PrintStmt(new VarExp("v")))));
//
//        Controller ctr7 = null;
//
//        try {
//            ex7.typeCheck(new MyDictionary<>());
//            PrgState prgState7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex7, new MyDictionary<>(), new MyHeap<>());
//            IRepo repo7 = new Repo(prgState7, "log7.txt");
//            ctr7 = new Controller(repo7);
//            System.out.println("Ex7 passed the type check and prg state has been created");
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//
//        IStmt ex8 = new CompStmt(new VarDeclStmt("v",new IntType()),
//                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(5))),
//                        new CompStmt(new VarDeclStmt("a" , new RefType(new IntType())),
//                                new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(22))),
//                                        new CompStmt(new forkStmt(new CompStmt(new rW("a", new ValueExp(new IntValue(30))),
//                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
//                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a"))))))),
//                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a")))))))));
//
//        Controller ctr8 = null;
//        try {
//            ex8.typeCheck(new MyDictionary<>());
//
//            PrgState prgState8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex8, new MyDictionary<>(), new MyHeap<>());
//            IRepo repo8 = new Repo(prgState8, "log8.txt");
//            ctr8 = new Controller(repo8);
//            System.out.println("Ex8 passed the type check and prg state has been created");
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//
//
//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
//        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
//        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
//        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
//        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
//        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
//        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
//        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
//        menu.show();
//    }
//}