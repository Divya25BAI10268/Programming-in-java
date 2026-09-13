import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentTaskManager extends JFrame {

    // Store all tasks
    private ArrayList<Task> tasks = new ArrayList<>();

    // Main window
    public StudentTaskManager() {

        setTitle("Student Task Manager");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMainScreen();
    }

    private void createMainScreen() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // Heading
        JLabel heading = new JLabel(
                "STUDENT TASK MANAGER",
                SwingConstants.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(new Color(40, 40, 40));
        heading.setBorder(
                BorderFactory.createEmptyBorder(25, 10, 20, 10)
        );

        mainPanel.add(heading, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel(
                new GridLayout(2, 2, 25, 25)
        );

        buttonPanel.setBackground(
                new Color(245, 247, 250)
        );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        40, 100, 40, 100
                )
        );

        JButton addTaskButton = createButton("ADD TASK");
        JButton viewTaskButton = createButton("MY TASKS");
        JButton completedButton = createButton("COMPLETED TASKS");
        JButton exitButton = createButton("EXIT");

        buttonPanel.add(addTaskButton);
        buttonPanel.add(viewTaskButton);
        buttonPanel.add(completedButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
                "Stay organized • Complete your tasks • Achieve your goals",
                SwingConstants.CENTER
        );

        footer.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        footer.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 20, 10
                )
        );

        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);

        addTaskButton.addActionListener(
                e -> showAddTaskWindow()
        );

        viewTaskButton.addActionListener(
                e -> showAllTasks()
        );

        completedButton.addActionListener(
                e -> showCompletedTasks()
        );

        exitButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        button.setFocusPainted(false);

        button.setBackground(Color.WHITE);

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(200, 200, 200),
                        1
                )
        );

        return button;
    }

    private void showAddTaskWindow() {

        JTextField titleField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField deadlineField = new JTextField();

        JPanel panel = new JPanel(
                new GridLayout(0, 1, 5, 5)
        );

        panel.add(new JLabel("Task Title:"));
        panel.add(titleField);

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        panel.add(new JLabel("Deadline (DD-MM-YYYY):"));
        panel.add(deadlineField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add New Task",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {

            String title = titleField.getText().trim();
            String description = descriptionField.getText().trim();
            String deadline = deadlineField.getText().trim();

            // Check empty fields
            if (title.isEmpty() || deadline.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the task title and deadline.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // Generate Task ID
            int taskId = tasks.size() + 1;

            Task task = new Task(
                    taskId,
                    title,
                    description,
                    deadline
            );

            tasks.add(task);

            JOptionPane.showMessageDialog(
                    this,
                    "Task added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void showAllTasks() {

        JFrame taskWindow = new JFrame("My Tasks");

        taskWindow.setSize(700, 500);
        taskWindow.setLocationRelativeTo(this);
        taskWindow.setLayout(new BorderLayout());

        JTextArea taskArea = new JTextArea();

        taskArea.setEditable(false);

        taskArea.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        // Display tasks
        updateTaskArea(taskArea);

        // Complete button
        JButton completeButton =
                new JButton("MARK TASK COMPLETED");

        completeButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        completeButton.setFocusPainted(false);

        // Delete button
        JButton deleteButton =
                new JButton("DELETE TASK");

        deleteButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        deleteButton.setFocusPainted(false);

        // Buttons panel
        JPanel bottomPanel = new JPanel();

        bottomPanel.add(completeButton);
        bottomPanel.add(deleteButton);

        taskWindow.add(
                new JScrollPane(taskArea),
                BorderLayout.CENTER
        );

        taskWindow.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        completeButton.addActionListener(e -> {

            if (tasks.isEmpty()) {

                JOptionPane.showMessageDialog(
                        taskWindow,
                        "There are no tasks to complete."
                );

                return;
            }

            String input = JOptionPane.showInputDialog(
                    taskWindow,
                    "Enter Task ID to mark as completed:"
            );

            if (input == null) {
                return;
            }

            try {

                int taskId = Integer.parseInt(input);

                boolean found = false;

                for (Task task : tasks) {

                    if (task.getTaskId() == taskId) {

                        found = true;

                        if (task.isCompleted()) {

                            JOptionPane.showMessageDialog(
                                    taskWindow,
                                    "This task is already completed!"
                            );

                        } else {

                            task.markCompleted();

                            JOptionPane.showMessageDialog(
                                    taskWindow,
                                    "Task marked as completed!"
                            );

                            updateTaskArea(taskArea);
                        }

                        break;
                    }
                }

                if (!found) {

                    JOptionPane.showMessageDialog(
                            taskWindow,
                            "Task ID not found!"
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        taskWindow,
                        "Please enter a valid Task ID."
                );
            }
        });

        deleteButton.addActionListener(e -> {

            if (tasks.isEmpty()) {

                JOptionPane.showMessageDialog(
                        taskWindow,
                        "There are no tasks to delete."
                );

                return;
            }

            String input = JOptionPane.showInputDialog(
                    taskWindow,
                    "Enter Task ID to delete:"
            );

            if (input == null) {
                return;
            }

            try {

                int taskId = Integer.parseInt(input);

                Task taskToDelete = null;

                for (Task task : tasks) {

                    if (task.getTaskId() == taskId) {
                        taskToDelete = task;
                        break;
                    }
                }

                if (taskToDelete != null) {

                    int choice = JOptionPane.showConfirmDialog(
                            taskWindow,
                            "Are you sure you want to delete this task?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (choice == JOptionPane.YES_OPTION) {

                        tasks.remove(taskToDelete);

                        JOptionPane.showMessageDialog(
                                taskWindow,
                                "Task deleted successfully!"
                        );

                        updateTaskArea(taskArea);
                    }

                } else {

                    JOptionPane.showMessageDialog(
                            taskWindow,
                            "Task ID not found!"
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        taskWindow,
                        "Please enter a valid Task ID."
                );
            }
        });

        taskWindow.setVisible(true);
    }

    private void updateTaskArea(JTextArea taskArea) {

        if (tasks.isEmpty()) {

            taskArea.setText(
                    "\n\n              No tasks available.\n\n" +
                            "              Add a task to get started!"
            );

            return;
        }

        StringBuilder text = new StringBuilder();

        for (Task task : tasks) {

            text.append("Task ID: ")
                    .append(task.getTaskId())
                    .append("\n");

            text.append("Title: ")
                    .append(task.getTitle())
                    .append("\n");

            text.append("Description: ")
                    .append(task.getDescription())
                    .append("\n");

            text.append("Deadline: ")
                    .append(task.getDeadline())
                    .append("\n");

            text.append("Status: ")
                    .append(
                            task.isCompleted()
                                    ? "COMPLETED"
                                    : "PENDING"
                    )
                    .append("\n");

            text.append(
                    "----------------------------------------\n\n"
            );
        }

        taskArea.setText(text.toString());
    }

    private void showCompletedTasks() {

        JFrame completedWindow =
                new JFrame("Completed Tasks");

        completedWindow.setSize(650, 450);

        completedWindow.setLocationRelativeTo(this);

        JTextArea area = new JTextArea();

        area.setEditable(false);

        area.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        StringBuilder text = new StringBuilder();

        boolean found = false;

        for (Task task : tasks) {

            if (task.isCompleted()) {

                found = true;

                text.append("Task ID: ")
                        .append(task.getTaskId())
                        .append("\n");

                text.append("Title: ")
                        .append(task.getTitle())
                        .append("\n");

                text.append("Description: ")
                        .append(task.getDescription())
                        .append("\n");

                text.append("Deadline: ")
                        .append(task.getDeadline())
                        .append("\n");

                text.append("Status: COMPLETED\n");

                text.append(
                        "----------------------------------------\n\n"
                );
            }
        }

        if (!found) {

            text.append(
                    "\n\n              No completed tasks yet."
            );
        }

        area.setText(text.toString());

        completedWindow.add(
                new JScrollPane(area)
        );

        completedWindow.setVisible(true);
    }
}