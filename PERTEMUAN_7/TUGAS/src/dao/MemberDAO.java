package PERTEMUAN_7.TUGAS.src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import PERTEMUAN_7.TUGAS.src.db.DatabaseConnection;
import PERTEMUAN_7.TUGAS.src.model.Member;

public class MemberDAO {
    public void addMember(Member member) throws SQLException {
        String sql = "INSERT INTO members (name, email, phone, address, membership_type, interests, active_status, join_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getEmail());
            stmt.setString(3, member.getPhone());
            stmt.setString(4, member.getAddress());
            stmt.setString(5, member.getMembershipType());
            stmt.setString(6, member.getInterests());
            stmt.setBoolean(7, member.isActiveStatus());
            stmt.setDate(8, new java.sql.Date(member.getJoinDate().getTime()));
            
            stmt.executeUpdate();
        }
    }
    
    public void updateMember(Member member) throws SQLException {
        String sql = "UPDATE members SET name=?, email=?, phone=?, address=?, " +
                    "membership_type=?, interests=?, active_status=?, join_date=? WHERE member_id=?";
                    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getEmail());
            stmt.setString(3, member.getPhone());
            stmt.setString(4, member.getAddress());
            stmt.setString(5, member.getMembershipType());
            stmt.setString(6, member.getInterests());
            stmt.setBoolean(7, member.isActiveStatus());
            stmt.setDate(8, new java.sql.Date(member.getJoinDate().getTime()));
            stmt.setInt(9, member.getMemberId());
            
            stmt.executeUpdate();
        }
    }
    
    public void deleteMember(int memberId) throws SQLException {
        String sql = "DELETE FROM members WHERE member_id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, memberId);
            stmt.executeUpdate();
        }
    }
    
    public List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Member member = new Member(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("membership_type"),
                    rs.getString("interests"),
                    rs.getBoolean("active_status"),
                    rs.getDate("join_date")
                );
                member.setMemberId(rs.getInt("member_id"));
                members.add(member);
            }
        }
        return members;
    }
}

