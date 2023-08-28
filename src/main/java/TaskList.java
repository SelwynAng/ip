import java.util.ArrayList;

/**
 * This is the TaskList class
 * @author Selwyn
 */
public class TaskList {
    /**
     * This is the Task arraylist
     */
    protected ArrayList<Task> tasks;

    /**
     * This keeps track of the number of tasks in the list
     */
    protected int numTasks;

    /**
     * Constructor creates an array of size 100 when there is no argument provided
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        numTasks = 0;
    }

    /**
     * This method will help to create a task and adds that task to the task arraylist
     * @param taskType Enum TaskType representing the type of task to create
     * @param args Details of the task to add
     */
    protected void addTask(TaskType taskType, String args) {
        Task newTask;

        try {
            switch (taskType) {
            case TODO:
                newTask = createTodo(args);
                break;
            case DEADLINE:
                newTask = createDeadline(args);
                break;
            case EVENT:
                newTask = createEvent(args);
                break;
            default:
                throw new DukeException("I can't create this task type!");
            }

            System.out.println("Got it. I've added this task:");
            System.out.print("   ");
            System.out.println(newTask.toString());

            this.tasks.add(newTask);
            this.numTasks++;

            if (numTasks == 1) {
                System.out.println("Now you have " + numTasks + " task in the list.");
            } else {
                System.out.println("Now you have " + numTasks + " tasks in the list.");
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This is the method to create a todo task
     * @param args Details of the task
     * @return A todo task
     * @throws DukeException An exception related to the creation of a todo task
     */
    protected Todo createTodo(String args) throws DukeException{
        if (args == null || args.isEmpty()) {
            throw new DukeException("Todo tasks should be created in this format: todo [name]");
        }
        return new Todo(args);
    }

    /**
     * This is the method to create a deadline task
     * @param args Details of the task
     * @return A deadline task
     * @throws DukeException An exception related to the creation of a deadline task
     */
    protected Deadline createDeadline(String args) throws DukeException {
        String[] detailsAndDeadline = args.split("/by", 2);
        if (detailsAndDeadline.length != 2) {
            throw new DukeException("Deadline tasks should be created in this format: deadline [name] /by [date]");
        }
        String details = detailsAndDeadline[0].trim();
        String endDateTime = detailsAndDeadline[1].trim();
        return new Deadline(details, endDateTime);
    }

    /**
     * This is the method to create an event task
     * @param args Details of the task
     * @return An event task
     * @throws DukeException An exception related to the creation of an event task
     */
    protected Event createEvent(String args) throws DukeException {
        String eventMsg = "Event tasks should be created in this format: event [name] /from [start time] /to [end time]";
        String[] detailsAndStartEnd = args.split("/from", 2);
        if (detailsAndStartEnd.length != 2) {
            throw new DukeException(eventMsg);
        }
        String details = detailsAndStartEnd[0].trim();

        String[] startAndEnd = detailsAndStartEnd[1].split("/to", 2);
        if (startAndEnd.length != 2) {
            throw new DukeException(eventMsg);
        }

        String start = startAndEnd[0].trim();
        String end = startAndEnd[1].trim();
        return new Event(details, start, end);
    }

    /**
     * This method deletes a certain task in the task list based on a task number given
     * @param args Details regarding which task to delete
     * @throws DukeException An exception related to the deletion of a task
     */
    protected void deleteTask(String args) throws DukeException {
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(args);

            if (taskNumber <= 0) {
                throw new DukeException("Number must be more 1 or more!");
            } else if (this.exceedsSizeOfTaskList(taskNumber)) {
                throw new DukeException("Number is higher than current size of task list!");
            }

            System.out.println("Noted. I've removed this task:");
            System.out.print("   ");
            System.out.println(this.tasks.get(taskNumber- 1).toString());

            this.tasks.remove(taskNumber - 1);
            this.numTasks--;

            if (numTasks == 1 || numTasks == 0) {
                System.out.println("Now you have " + numTasks + " task in the list.");
            } else {
                System.out.println("Now you have " + numTasks + " tasks in the list.");
            }

        } catch (NumberFormatException e) {
            throw new DukeException("Deleting task should be in this format: delete [task number]");
        }
    }

    /**
     * This method toggles the done status of a specific task
     * @param args Details regarding which task to toggle
     * @param state Done status of the specific task
     * @throws DukeException An exception related to the toggling of a task
     */
    protected void changeTaskDoneStatus(String args, boolean state) throws DukeException {
        int taskNumber;

        try {
            taskNumber = Integer.parseInt(args);

            if (taskNumber <= 0) {
                throw new DukeException("Number must be more 1 or more!");
            } else if (this.exceedsSizeOfTaskList(taskNumber)) {
                throw new DukeException("Number is higher than current size of task list!");
            }

            if (state) {
                this.tasks.get(taskNumber - 1).markDone();
                System.out.println("Nice! I've marked this task done:");
            } else {
                this.tasks.get(taskNumber - 1).markUndone();
                System.out.println("OK, I've marked this task as not done yet:");
            }
            System.out.println(this.tasks.get(taskNumber - 1).toString());
        } catch (NumberFormatException e) {
            throw new DukeException("Marking/unmarking tasks should be in this format: mark/unmark [task number]");
        }
    }

    /**
     * This method displays and prints all the tasks in the task list
     */
    protected void displayTaskList() {
        if (this.numTasks == 0 || this.numTasks == 1) {
            System.out.println("Here is the task in your list:");
        } else {
            System.out.println("Here are the tasks in your list:");
        }
        for (int i = 0; i < this.numTasks; i++) {
            int bullet = i + 1;
            System.out.print(bullet + ". ");
            System.out.println(this.tasks.get(i).toString());
        }
    }

    /**
     * This method checks if number provided exceeds size of task list
     * @param index
     * @return  Represents whether provided number exceeds size of task list
     */
    protected boolean exceedsSizeOfTaskList(int index) {
        return index > numTasks;
    }
}