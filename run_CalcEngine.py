import subprocess
import sys

# Check argument count
if len(sys.argv) != 5:
    print("Usage: python run_CalcEngine.py <int/float> <add/sub/mul/div> <operand1> <operand2>")
    sys.exit(1)

arg_type = sys.argv[1]
operation = sys.argv[2]
operand1 = sys.argv[3]
operand2 = sys.argv[4]

# === Step 1: Compile all .java files ===
compile_cmd = [
    "javac",
    "-cp", "arbitraryarithmetic/aarithmetic.jar",
    "arbitraryarithmetic/AFloat.java",
    "arbitraryarithmetic/AInteger.java",
    "MyInfArith.java"
]

compile_result = subprocess.run(compile_cmd, capture_output=True, text=True)
if compile_result.returncode != 0:
    print("Compilation Error:\n", compile_result.stderr)
    sys.exit(1)

# === Step 2: Run the main Java class ===
run_cmd = [
    "java",
    "-cp", ".;arbitraryarithmetic/aarithmetic.jar",  # Use ':' instead of ';' on macOS/Linux
    "MyInfArith",
    arg_type, operation, operand1, operand2
]

run_result = subprocess.run(run_cmd, capture_output=True, text=True)

if run_result.returncode == 0:
    print( run_result.stdout.strip())
else:
    print("Runtime Error:\n", run_result.stderr.strip())
