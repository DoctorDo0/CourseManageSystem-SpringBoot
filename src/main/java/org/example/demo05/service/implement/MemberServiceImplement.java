package org.example.demo05.service.implement;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.demo05.dao.MemberDAO;
import org.example.demo05.entity.Member;
import org.example.demo05.entity.bean.MemberBean;
import org.example.demo05.service.MemberService;
import org.example.demo05.utils.AuditEntity;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MemberServiceImplement implements MemberService {
    //强加密器
    private static final PasswordEncryptor pe = new StrongPasswordEncryptor();
    private MemberDAO memberDAO;

    @Autowired
    public void setMemberMapper(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public List<Member> getMembers(Page<?> page, MemberBean memberBean) {
        try (Page<?> _ = PageHelper.startPage(page.getPageNum(), page.getPageSize())) {
            return memberDAO.selectAll(memberBean);
        }
    }

    @Override
    public Integer getMembersCount(MemberBean memberBean) {
        return memberDAO.getMembersCount(memberBean);
    }

    @Override
    public Integer addMember(Member member) {
        return memberDAO.insert(member);
    }

    @Override
    public Integer updateMember(Member member) {
        if (!StringUtils.hasText(member.getMemberPassword())) {
            member.setMemberPassword(null);
        } else {
            member.setMemberPassword(pe.encryptPassword(member.getMemberPassword()));
        }
        return memberDAO.updateByPrimaryKey(member);
    }

    @Override
    public Integer deleteMember(Integer[] ids, AuditEntity auditEntity) {
        return memberDAO.deleteByPrimaryKey(ids, auditEntity);
    }

    @Override
    public Integer restoreMember(Integer[] ids, AuditEntity auditEntity) {
        return memberDAO.restoreByPrimaryKey(ids, auditEntity);
    }

    @Override
    public int batchSave(List<Member> members) {
        int count = 0;
        for (Member member : members) {
            //此处需要使用代理，如果不使用代理，无法触发aop，不会自动添加审计字段

            //TODO:
            //暂时将初始密码设置为123456
            member.setMemberPassword("123456");

            int success = self().addMember(member);
            if (success != 0) {
                count = count + success;
            }
        }
        return count;
    }

    private MemberService self() {
        return (MemberService) AopContext.currentProxy();
    }
}
