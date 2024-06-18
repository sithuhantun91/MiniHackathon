import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MiniHackathon1Ver2 {
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
        try(CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                .withSkipLines(1)
                .build())
        {
            String [] nextLine;

            //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
                Company company = new Company();
                company.setIndex(Integer.parseInt(nextLine[0]));
                company.setOrganizationId(nextLine[1]);
                company.setSalesRepId(nextLine[2]);
                company.setName(nextLine[3]);
                company.setWebsite(nextLine[4]);
                company.setCountry(nextLine[5]);
                company.setDescription(nextLine[6]);
                company.setFounded(nextLine[7]);
                company.setIndustry(nextLine[8]);
                company.setNumberOfEmployees(Integer.parseInt(nextLine[9]));
                companyList.add(company);
            }
            return companyList;
        }
        catch (Exception e) {
            throw e;
        }
    }

    //Read data from SalesReps.csv file
    private static List<SalesRep> getSalesRepList(String filePath) throws Exception {
        List<SalesRep> salesRepList = new ArrayList<>();
        try(CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                .withSkipLines(1)
                .build())
        {
            String [] nextLine;

            //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
                SalesRep salesRep = new SalesRep();
                salesRep.setUserId(nextLine[0]);
                salesRep.setFirstName(nextLine[1]);
                salesRep.setLastName(nextLine[2]);
                salesRep.setEmail(nextLine[3]);
                salesRep.setPhone(nextLine[4]);
                salesRep.setDateOfBirth(nextLine[5]);
                salesRepList.add(salesRep);
            }
            return salesRepList;
        }
        catch (Exception e) {
            throw e;
        }
    }
}