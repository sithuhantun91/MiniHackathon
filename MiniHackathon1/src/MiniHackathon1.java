import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MiniHackathon1 {
    //Regex to ignore comma inside double quotes
    private static final String REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    public static void main(String[] args) {
        List<Company> companyList = new ArrayList<>();
        List<SalesRep> salesRepList = new ArrayList<>();
        String choice = "";
        try {
            //Read data from csv files
            String location = "./resources/Company.csv";
            companyList = getCompanyList(location);
            location = "./resources/SalesReps.csv";
            salesRepList = getSalesRepList(location);

            //find Sales Person by last name
            Scanner sc = new Scanner(System.in);
            do {
                //get Sales Person's Last name from user input
                System.out.println("Enter Sales Person's Last name to search company data: ");
                String lastName = sc.nextLine().toLowerCase();

                //find Sales Person
                List<SalesRep> salesReps = new ArrayList<>();
                salesReps = salesRepList.stream()
                        .filter(sr -> sr.getLastName().toLowerCase().equals(lastName))
                        .collect(Collectors.toList());
                System.out.println(salesReps.size() + " SalesRep found");
                System.out.println();

                //find associated companies for each sales person
                for (SalesRep sr : salesReps) {
                    System.out.println("Name: " + sr.getFirstName() + " " + sr.getLastName());
                    List<Company> companies = new ArrayList<>();
                    companies = companyList.stream()
                            .filter(company -> company.getSalesRepId().equals(sr.getUserId()))
                            .collect(Collectors.toList());
                    for (Company company : companies) {
                        System.out.println(company.toString());
                    }
                    System.out.println();
                }

                //Ask user want to continue search again or not
                System.out.println("Do you want to continue? (Y/N)");
                choice = sc.nextLine().toLowerCase();
                System.out.println();
            } while (choice.equals("yes") || choice.equals("y"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    //Read data from Company.csv file
    private static List<Company> getCompanyList(String filePath) throws Exception {
        List<Company> companyList = new ArrayList<>();
        File file = new File(filePath);
        Scanner input = new Scanner(file);
        input.nextLine();
        while (input.hasNextLine()) {
            String Line = input.nextLine();
            String[] splitedLine = Line.split(REGEX);
            Company company = new Company();
            company.setIndex(Integer.parseInt(splitedLine[0]));
            company.setOrganizationId(splitedLine[1]);
            company.setSalesRepId(splitedLine[2]);
            company.setName(splitedLine[3]);
            company.setWebsite(splitedLine[4]);
            company.setCountry(splitedLine[5]);
            company.setDescription(splitedLine[6]);
            company.setFounded(splitedLine[7]);
            company.setIndustry(splitedLine[8]);
            company.setNumberOfEmployees(Integer.parseInt(splitedLine[9]));
            companyList.add(company);
        }
        return companyList;
    }

    //Read data from SalesReps.csv file
    private static List<SalesRep> getSalesRepList(String filePath) throws Exception {
        List<SalesRep> salesRepList = new ArrayList<>();
        File file = new File(filePath);
        Scanner input = new Scanner(file);
        input.nextLine();
        while (input.hasNextLine()) {
            String Line = input.nextLine();
            String[] splitedLine = Line.split(REGEX);
            SalesRep salesRep = new SalesRep();
            salesRep.setUserId(splitedLine[0]);
            salesRep.setFirstName(splitedLine[1]);
            salesRep.setLastName(splitedLine[2]);
            salesRep.setEmail(splitedLine[3]);
            salesRep.setPhone(splitedLine[4]);
            salesRep.setDateOfBirth(splitedLine[5]);
            salesRepList.add(salesRep);
        }
        return salesRepList;
    }
}

