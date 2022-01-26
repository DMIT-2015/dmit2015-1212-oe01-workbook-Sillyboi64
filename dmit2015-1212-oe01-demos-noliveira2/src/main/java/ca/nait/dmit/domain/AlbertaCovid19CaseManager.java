package ca.nait.dmit.domain;

import lombok.Getter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlbertaCovid19CaseManager {

    private static AlbertaCovid19CaseManager instance;

    public static AlbertaCovid19CaseManager getInstance() throws IOException {
        if(instance == null){
            synchronized (AlbertaCovid19CaseManager.class) {
                if(instance == null){
                    instance = new AlbertaCovid19CaseManager();
                }
            }
        }
        return instance;
    }

    @Getter
    private List<AlbertaCovid19Case> albertaCovid19CaseList = new ArrayList<>();

    private AlbertaCovid19CaseManager() throws IOException {
        try(var reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(
                "" + "/data/covid-19-alberta-statistics-data.csv")))) {
            String lineText;
            // Declare a delimiter that looks for a comma inside a value
            final var DELIMITER = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
            // We can skip the first lines since it contains header columns
            reader.readLine();
            var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            while ((lineText = reader.readLine()) != null) {
                // Create an object for each row in the file
                AlbertaCovid19Case currentCase = new AlbertaCovid19Case();
                // the -limit allows for any number of fields and not discard empty fields
                String[] values = lineText.split(DELIMITER, -1);
                // Column order of fields "","Date reported","Alberta Health Services Zone","Gender","Age group","Case status","Case type"
                // 0 - "Id"
                // 1 - "Date Reported"
                // 2 - "Alberta Health Services Zone"
                // 3 - "Gender"
                // 4 - "Age Group"
                // 5 - "Case Status"
                // 6 - "Case Type"
                currentCase.setId(Integer.parseInt(values[0].replaceAll("\"","")));//important to see how string is parsed into an int
                currentCase.setDateReported(LocalDate.parse(values[1].replaceAll("\"",""), dateTimeFormatter));
                currentCase.setAhsZone(values[2].replaceAll("\"",""));
                currentCase.setGender(values[3].replaceAll("\"",""));
                currentCase.setAgeGroup(values[4].replaceAll("\"",""));
                currentCase.setCaseStatus(values[5].replaceAll("\"",""));
                currentCase.setCaseType(values[6].replaceAll("\"",""));
                //adds object to list
                albertaCovid19CaseList.add(currentCase);
            }
        }
    }

    public List<String> findDistinctAhsZone() {
        return albertaCovid19CaseList
                .stream()
                //.map(AlbertaCovid19Case::getAhsZone) method expression does the same thing below
                .map(item -> item.getAhsZone())//lambda expression
                .distinct()
                .filter(item -> item.isEmpty() == false)//filters out the extra 1 you would get instead of 7 there are 6
                .sorted()
                .collect(Collectors.toList());
    }

    public long findActiveCaseCount() {
        return albertaCovid19CaseList
                .stream()
                .filter(item -> item.getCaseStatus().equalsIgnoreCase("Active"))
                //finds count of active cases
                .count();
    }

    public long activeCaseCountByAhsZone(String ahsZone) {
        return albertaCovid19CaseList
                .stream()
                //.filter(item -> item.getCaseStatus().equalsIgnoreCase("Active")
                //        && item.getAhsZone().equalsIgnoreCase(ahsZone)) also works like down below
                .filter(item -> item.getCaseStatus().equalsIgnoreCase("Active"))
                .filter(item -> item.getAhsZone().equalsIgnoreCase(ahsZone))
                .count();
    }

    public long caseReportedCountByAhsZoneDateRange(String ahsZone, LocalDate fromDate, LocalDate toDate) {
        return albertaCovid19CaseList
                .stream()
                .filter(item -> item.getAhsZone().equalsIgnoreCase(ahsZone))
                .filter(item -> !item.getDateReported().isBefore(fromDate) && !item.getDateReported().isAfter(toDate))
                .count();
    }

}
