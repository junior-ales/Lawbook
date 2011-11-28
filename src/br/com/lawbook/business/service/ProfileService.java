package br.com.lawbook.business.service;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lawbook.dao.ProfileDAO;
import br.com.lawbook.dao.impl.ProfileDAOImpl;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-16
 */
public final class ProfileService implements Serializable {

	private final ProfileDAO dao;
	private static ProfileService instance;
	private static Profile publicProfile;
	private final static Logger LOG = Logger.getLogger("ProfileService");
	private static final long serialVersionUID = -6480090150580865692L;

	private ProfileService() {
		this.dao = new ProfileDAOImpl();
	}

	public static ProfileService getInstance() {
		if (instance == null) {
			instance = new ProfileService();
		}
		return instance;
	}

	public void create(final Profile profile) throws IllegalArgumentException, HibernateException  {
		JavaUtil.validateParameter(profile, "ProfileService: create: profile");
		this.dao.create(profile);
	}

	public void update(final Profile profile) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(profile, "ProfileService: update: profile");
		this.dao.update(profile);
	}

	public Profile getAuthorizedUserProfile() throws IllegalArgumentException, HibernateException {
		final SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) throw new IllegalArgumentException("SecurityContext is null");

		final Authentication authentication = context.getAuthentication();
        if (authentication == null) throw new IllegalArgumentException("Authentication is null");

        final String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
        return this.getProfileByUserName(username);
	}

	public Profile getProfileById(final Long id) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(id, "ProfileService: getProfileById: id");
		return this.dao.getProfileById(id);
	}

	public Profile getProfileByUserName(final String userName) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userName, "ProfileService: getProfileByUserName: userName");
		return this.dao.getProfileByUserName(userName);
	}

	public Profile getProfileByUserId(final Long userId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userId, "ProfileService: getProfileByUserId: userId");
		return this.dao.getProfileByUserId(userId);
	}

	public List<Profile> getAll() throws HibernateException {
		return this.dao.getAll();
	}

	public Profile getPublicProfile() {
		if (publicProfile == null) {
			try {
				publicProfile = this.getProfileByUserName("public");
			} catch (final HibernateException e) {
				LOG.severe(e.getMessage());
			}
		}
		return publicProfile;
	}

	public void friendship(final Profile profile1, final Profile profile2) throws IllegalArgumentException, HibernateException {
		if (profile1.getId().equals(profile2.getId()))
			throw new IllegalArgumentException("You cannot connect to yourself");

		final List<Profile> friends = profile1.getFriends();
		for (final Profile p : friends) {
			if (p.getId().equals(profile2.getId())) {
				throw new IllegalArgumentException("This user is already connected to you");
			}
		}

		friends.add(profile2);
		profile1.setFriends(friends);
		this.update(profile1);
	}
	
	public boolean cpfValidation(String strCpf) throws IllegalArgumentException {    
        JavaUtil.validateParameter(strCpf, "Required paramenter: ProfileService.cpfValidation.strCpf");
        if (strCpf.length() != 11) throw new IllegalArgumentException("Tamanho de CPF inválido");
        
        int d1, d2;    
        int digito1, digito2, resto;    
        int digitoCPF;    
        String nDigResult;    
    
        d1 = d2 = 0;    
        digito1 = digito2 = resto = 0;    
    
        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {    
            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();    
            d1 = d1 + (11 - nCount) * digitoCPF;    
            d2 = d2 + (12 - nCount) * digitoCPF;    
        }    
        resto = (d1 % 11);    
        if (resto < 2)  
            digito1 = 0;    
        else     
            digito1 = 11 - resto;    
    
        d2 += 2 * digito1;    
        resto = (d2 % 11);  
        
        if (resto < 2) 
        	digito2 = 0;    
        else 
        	digito2 = 11 - resto;    
    
        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());    
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);    
        return nDigVerific.equals(nDigResult);    
    }    
	
     public boolean cnpjValidation(String cnpj) throws IllegalArgumentException {
    	 JavaUtil.validateParameter(cnpj, "Required paramenter: ProfileService.cnpjValidation.cnpj");
         if (cnpj.length() != 14) throw new IllegalArgumentException("Tamanho de CNPJ inválido");

          int soma = 0;
          String cnpj_calc = cnpj.substring(0, 12);

          char chr_cnpj[] = cnpj.toCharArray();
          for(int i = 0; i < 4; i++)
               if(chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                    soma += (chr_cnpj[i] - 48) * (6 - (i + 1));

         for(int i = 0; i < 8; i++)
              if(chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
                    soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));

         int dig = 11 - soma % 11;
         cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
         soma = 0;
         for(int i = 0; i < 5; i++)
              if(chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                   soma += (chr_cnpj[i] - 48) * (7 - (i + 1));

         for(int i = 0; i < 8; i++)
              if(chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
                   soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));

         dig = 11 - soma % 11;
         cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();

         return cnpj.equals(cnpj_calc);
     }
}
