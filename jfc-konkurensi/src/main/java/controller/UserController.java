package controller;

import model.MyBatisUtil;
import model.User;
import model.UserMapper;
import view.UserView;
import view.UserPdf;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserController {
    private UserView view;
    private UserMapper mapper;
    private ExecutorService executor;

    public UserController(UserView view, UserMapper mapper) {
        this.view = view;
        this.mapper = mapper;
        this.executor = Executors.newFixedThreadPool(4);

        this.view.addAddUserListener(new AddUserListener());
        this.view.addUpdateUserListener(new UpdateUserListener());
        this.view.addDeleteUserListener(new DeleteUserListener());
        this.view.addRefreshUserListener(new RefreshListener());
        this.view.addExportPdfListener(new ExportPdfListener());

        // Set initial progress bar state
        view.setProgressBarMessage("Ready");
        view.setProgressBarValue(0);
    }

    private void updateProgress(String message, int value) {
        SwingUtilities.invokeLater(() -> {
            view.setProgressBarMessage(message);
            view.setProgressBarValue(value);
        });
    }

    class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameINput();
            String email = view.getEmailINput();

            if (!name.isEmpty() && !email.isEmpty()) {
                executor.execute(() -> {
                    updateProgress("Adding User...", 0);
                    SqlSession session = MyBatisUtil.getSqlSession();
                    try {
                        User user = new User();
                        user.setName(name);
                        user.setEmail(email);

                        updateProgress("Processing user data...", 50);
                        UserMapper mapper = session.getMapper(UserMapper.class);
                        mapper.insert(user);
                        session.commit();
                        updateProgress("User added successfully", 100);

                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(view, "User added successfully!");
                        });
                    } catch (Exception ex) {
                        session.rollback();
                        updateProgress("Error: " + ex.getMessage(), 0);
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
                        });
                    } finally {
                        session.close();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(view, "Please fill in all fields.");
            }
        }
    }

    class UpdateUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameINput();
            String email = view.getEmailINput();
            int userId = view.getSelectedUserId();

            if (!name.isEmpty() && !email.isEmpty() && userId != -1) {
                executor.execute(() -> {
                    updateProgress("Updating User...", 0);
                    SqlSession session = MyBatisUtil.getSqlSession();
                    try {
                        User user = new User();
                        user.setId(userId);
                        user.setName(name);
                        user.setEmail(email);

                        updateProgress("Processing update...", 50);
                        UserMapper mapper = session.getMapper(UserMapper.class);
                        mapper.update(user);
                        session.commit();
                        updateProgress("User updated successfully", 100);

                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(view, "User updated successfully!");
                        });
                    } catch (Exception ex) {
                        session.rollback();
                        updateProgress("Error: " + ex.getMessage(), 0);
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
                        });
                    } finally {
                        session.close();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(view, "Please fill in all fields and select a user to update.");
            }
        }
    }

    class DeleteUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int userId = view.getSelectedUserId();

            if (userId == -1) {
                JOptionPane.showMessageDialog(view, "Please select a user to delete.");
                return;
            }

            executor.execute(() -> {
                updateProgress("Deleting User...", 0);
                SqlSession session = MyBatisUtil.getSqlSession();
                try {
                    updateProgress("Processing deletion...", 50);
                    UserMapper mapper = session.getMapper(UserMapper.class);
                    mapper.delete(userId);
                    session.commit();
                    updateProgress("User deleted successfully", 100);

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(view, "User deleted successfully!");
                    });
                } catch (Exception ex) {
                    session.rollback();
                    updateProgress("Error: " + ex.getMessage(), 0);
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
                    });
                } finally {
                    session.close();
                }
            });
        }
    }

    class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            executor.execute(() -> {
                updateProgress("Refreshing User List...", 0);
                SqlSession session = MyBatisUtil.getSqlSession();
                try {
                    updateProgress("Loading users...", 50);
                    UserMapper mapper = session.getMapper(UserMapper.class);
                    List<User> users = mapper.getAll();
                    updateProgress("User list refreshed successfully", 100);

                    SwingUtilities.invokeLater(() -> {
                        view.setUserList(users);
                    });
                } catch (Exception ex) {
                    updateProgress("Error: " + ex.getMessage(), 0);
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(view, "Error refreshing user list: " + ex.getMessage());
                    });
                } finally {
                    session.close();
                }
            });
        }
    }

    class ExportPdfListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            executor.execute(() -> {
                updateProgress("Starting PDF Export...", 0);
                SqlSession session = MyBatisUtil.getSqlSession();
                try {
                    updateProgress("Fetching user data...", 25);
                    UserMapper mapper = session.getMapper(UserMapper.class);
                    List<User> users = mapper.getAll();

                    if (users == null || users.isEmpty()) {
                        updateProgress("No data to export", 0);
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(view, "No user data to export.");
                        });
                        return;
                    }

                    updateProgress("Processing user data...", 50);
                    String[] userArray = users.stream()
                            .map(u -> u.getName() + " (" + u.getEmail() + ")")
                            .toArray(String[]::new);

                    updateProgress("Generating PDF...", 75);
                    UserPdf userPdf = new UserPdf();
                    userPdf.exportPdf(List.of(userArray));
                    updateProgress("PDF exported successfully", 100);

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(view, "PDF exported successfully!");
                    });
                } catch (Exception ex) {
                    updateProgress("Error: " + ex.getMessage(), 0);
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(view, "Error exporting PDF: " + ex.getMessage());
                    });
                } finally {
                    session.close();
                }
            });
        }
    }
}