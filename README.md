# CSE 248 Project ---> Teacherslink
<h2>07/09/2023 project part one </h2>



<h1> ExcelProcessor Class Summary</h1>



  <p>The ExcelProcessor class reads data from an Excel file (Instructors.xlsx) and extracts instructor data to populate a list of Instructor objects.</p>

  <h2>Key Components:</h2>
    <ul>
        <li><strong>Instructor Class:</strong>
            <ul>
                <li>Defines the properties of an instructor, such as ID number, home campus, business phone, name, address, courses, timings, etc.</li>
                <li>Contains getter and setter methods for each property.</li>
                <li>Overrides the toString() method to print the instructor's details in a formatted manner.</li>
            </ul>
        </li>
        <li><strong>Main Method:</strong>
            <ul>
                <li>Opens the Excel file (Instructors.xlsx) and reads the first sheet.</li>
                <li>Initializes an empty list (instructors) to store the extracted Instructor objects.</li>
                <li>Iterates over each row of the sheet using a loop.</li>
            </ul>
        </li>
        <li><strong>Row Processing:</strong>
            <ul>
                <li>For each row, it checks the first cell (ID cell).</li>
                <li>If this cell contains a valid ID (i.e., a string of exactly 8 characters), it processes the next few rows to extract the instructor's details.</li>
                <li>Uses the isValidId helper method to validate the ID.</li>
                <li>Populates an Instructor object with the extracted data.</li>
                <li>Adds the populated Instructor object to the instructors list.</li>
                <li>Skips the next two rows as they have already been processed for the current instructor.</li>
            </ul>
        </li>
        <li><strong>Printing the Data:</strong>
            <ul>
                <li>After extracting all instructors, it prints the number of instructors found.</li>
                <li>Iterates over the instructors list and prints the details of each instructor.</li>
            </ul>
        </li>
        <li><strong>Helper Method - isValidId:</strong>
            <ul>
                <li>This method checks if the provided value is a string of exactly 8 characters, ensuring it's a valid ID.</li>
            </ul>
        </li>
    </ul>

  <h2>Workflow:</h2>
      <ol>
        <li>Load the Excel file.</li>
        <li>Iterate over each row.</li>
        <li>For each row, check the first cell to see if it contains a valid ID.</li>
        <li>If valid, extract the instructor's details and populate an Instructor object.</li>
        <li>Add the instructor object to the list.</li>
        <li>Print the details of all extracted instructors.</li>
    </ol>

