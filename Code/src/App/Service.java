package App;

import Models.Mask.Mask;
import Models.Sanitizer.Sanitizer;
import Models.Person.Client;
import Models.Order.Acquisition;
import Repositories.AcquisitionRepository;
import Repositories.ClientRepository;
import Repositories.MaskRepository;
import Repositories.SanitizerRepository;

import java.io.IOException;
import java.util.InputMismatchException;

import static java.util.Arrays.sort;
public final class Service implements CRUD {
    private ClientRepository clientRepository = ClientRepository.getInstance();
    private MaskRepository maskRepository = MaskRepository.getInstance();
    private SanitizerRepository sanitizerRepository = SanitizerRepository.getInstance();
    private AcquisitionRepository acquisitionRepository = AcquisitionRepository.getInstance();
    private Audit audit = Audit.getInstance();
    private static Service instance;
    private Service() { }

    // static block initialization for exception handling
    static {
        try {
            instance = new Service();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating Service singleton instance");
        }
    }

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }
    public void configureTables()
    {
        clientRepository.createTable();
        maskRepository.createTable();
        sanitizerRepository.createTable();
        acquisitionRepository.createTable();
        try
        {
            audit.logAction("configure tables");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void printLocalData(){
        DataDictionary.getInstance().printAllData();
    }

    public void addMask(Mask newMask){
        if(newMask != null)
            maskRepository.addMask(newMask);
    }

    public void listMasks(){
        maskRepository.printMasks();
    }

    public void showMask(int type){
        Reader objReader = Reader.getInstance();
        try {
            if (type == 1) { //surgical
               maskRepository.showSGMask(objReader.readIndex());
            } else if (type == 2) { //polycarbonate
                maskRepository.showPCMask(objReader.readIndex());
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid input!");
        }
    }

    public void deleteMask(int type){
        Reader objReader = Reader.getInstance();
        try {
            if (type == 1) { //surgical
                if (maskRepository.printSGMasks())
                    maskRepository.deleteSGMask(objReader.readIndex());
            } else if (type == 2) { //polycarbonate
                if (maskRepository.printPCMasks())
                    maskRepository.deletePCMask(objReader.readIndex());
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid input!");
        }
    }

    public void addSanitizer(Sanitizer newSanitizer){
        if(newSanitizer != null)
            sanitizerRepository.addSanitizer(newSanitizer);
    }

    public void listSanitizers(){
        sanitizerRepository.printSanitizers();
    }

    public void showSanitizer(int type){
        Reader objReader = Reader.getInstance();
        try{
            if(type == 1){ //bacteria
                sanitizerRepository.showBCSanitizer(objReader.readIndex());
            }else if(type == 2){ //virus
                sanitizerRepository.showVSSanitizer(objReader.readIndex());
            }else if(type == 3){ //fungal
                sanitizerRepository.showFGSanitizer(objReader.readIndex());
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid input!");
        }
    }

    public void deleteSanitizer(int type){
        Reader objReader = Reader.getInstance();
        try{
            if(type == 1){ //bacteria
                if(sanitizerRepository.printBCSanitizers())
                    sanitizerRepository.deleteBCSanitizer(objReader.readIndex());
            }else if(type == 2){ //virus
                if(sanitizerRepository.printVSSanitizers())
                    sanitizerRepository.deleteVSSanitizer(objReader.readIndex());
            }else if(type == 3){ //fungal
                if(sanitizerRepository.printFGSanitizers())
                    sanitizerRepository.deleteFGSanitizer(objReader.readIndex());
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid input!");
        }
    }

    public void addClient(Client newClient){
        if(newClient != null)
            clientRepository.addClient(newClient);
    }

    public void listClients(){
        clientRepository.printClients();
    }

    public void updateClient(int ID, Client newClient){
        if(newClient != null)
            clientRepository.updateClient(ID, newClient);
    }

    public void addAcquisition(Acquisition newAcquisition){
        if(newAcquisition != null)
            acquisitionRepository.addAcquisition(newAcquisition);
    }
    public void listAcquisitions(){
        acquisitionRepository.printAquisitions();
    }

    public void incomeDate(int month, int year){
        double totalIncome = acquisitionRepository.getAcquisitionMonthYear(month, year);
        if(totalIncome > 0)
            System.out.println("Total income for month = " + month + ", year = " + year + " = " + totalIncome);
    }

    public void VAT(int year){
        double vat = acquisitionRepository.getAcquisitionYear(year);
        if (vat > 0)
            System.out.println("Total VAT in the year = " + year + " = " + vat);
    }


    public void sortedSanitizers(){
        DataDictionary dataDictionary = DataDictionary.getInstance();
        if(dataDictionary.emptyBacterialSanitizersPrice() &&
            dataDictionary.emptyFungalSanitizersPrice() &&
            dataDictionary.emptyVirusSanitizerPrice()){
                System.out.println("There are no sanitizers to be sorted!");
                return;
        }

        Sanitizer[] sortedSanitizers = sanitizerRepository.getAllSanitizers();
        if(sortedSanitizers != null) {
            sort(sortedSanitizers);

            System.out.println("Here are all the sorted sanitizers.");
            for (Sanitizer s : sortedSanitizers) {
                System.out.println(s);
            }
        }else{
            System.out.println("There were no sanitizers!");
        }
    }

    public void closeConnection()
    {
        try
        {
            DBFunctions.closeDatabaseConnection();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
