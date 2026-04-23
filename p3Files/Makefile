# Java compiler and runtime
JAVAC = javac
JAVA = java

# Source directory
SRC_DIR = tm

# Main class (inside tm package)
MAIN = Main

# Find all Java source files in tm/
SRC = $(wildcard $(SRC_DIR)/*.java)

# Convert .java files to .class files
CLASSES = $(SRC:.java=.class)

# Default target: compile all classes
all: $(CLASSES)

# Rule to compile each .java file in-place
$(SRC_DIR)/%.class: $(SRC_DIR)/%.java
	$(JAVAC) $<

# Run the program
run: all
	$(JAVA) tm.$(MAIN)

# Clean class files
clean:
	rm -f $(SRC_DIR)/*.class