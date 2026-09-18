// Login.java
public class Login {
    private String storedUser;
    private String storedPass;
    private String firstName;
    private String lastName;

    // check username
    public boolean checkUserName(String username) {
        if (username.contains("_") && username.length() <= 5) {
            return true;
        }
        return false;
    }
    // check password complexity
    public boolean checkPasswordComplexity(String password) {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        if (password.length() < 8) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            }
            if (Character.isDigit(c)) {
                hasNumber = true;
            }
            if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        return hasCapital && hasNumber && hasSpecial;
    }
    // check cell phone – simple version
    public boolean checkCellPhoneNumber(String cell) {
        // must start with +27 and then up to 10 digits
        if (cell.startsWith("+27") && cell.length() <= 13) {
            // check the rest are numbers
            String numbers = cell.substring(3);
            for (int i = 0; i < numbers.length(); i++) {
                if (!Character.isDigit(numbers.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    // register the user
    public String registerUser(String user, String pass, String cell, String fName, String lName) {
        if (!checkUserName(user)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(pass)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cell)) {
            return "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
        }
        // if we reach here everything is ok
        storedUser = user;
        storedPass = pass;
        firstName = fName;
        lastName = lName;
        return "Username successfully captured.\nPassword successfully captured.\nCell number successfully captured.";
    }
    // login
    public boolean loginUser(String user, String pass) {
        if (user.equals(storedUser) && pass.equals(storedPass)) {
            return true;
        }
        return false;
    }
    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}

