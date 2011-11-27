package br.com.lawbook.business.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.lawbook.business.service.ProcessService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-02
 *
 */
public class ProcessServiceTest {

	private final static ProfileService profileService = ProfileService.getInstance();
	private final static ProcessService processService = ProcessService.getInstance();
	private final static Profile adminProfile = profileService.getProfileByUserName("admin");
	private final static Profile publicProfile = profileService.getProfileByUserName("public");
	private final static Logger LOG = Logger.getLogger("ProcessServiceTest");

	@BeforeClass
	public static void create() {

		final Process process = new Process();

//		TODO it should be created in database before set to object
//		final Location local = new Location();
//		local.setCity("Curitiba");
//		local.setState("PR");
//		local.setCountry("Brasil");
//		process.setLocation(local);

		process.setResponsible(adminProfile);
		process.setPetitioner(publicProfile);
		process.setDefendant(adminProfile);
		process.setDescription("A PublicProfile process, requiring greater recognition in the Lawbook");
		process.setLegalArea("Labor");
		process.setTopic("Underpayment");
		process.setOpeningDate(getDate("02/01/2010"));
		process.setSituation("closed");

		saveProcess(process);
	}

	@Test
	public void getById() {
		final Long processId = processService.getMyProcesses(adminProfile).get(0).getId();
		final Process process = processService.getById(processId);
		assertNotNull(process);
		LOG.info("#### Process " + process.getId() + " fetched by responsible id successfully");
	}

	@Test
	public void getMyProcesses() {
		final List<Process> processes = processService.getMyProcesses(adminProfile);
		assertFalse(processes.isEmpty());
		LOG.info("#### All my processes fetched successfully");
	}

	public void update() {
		// TODO
	}

	private static Calendar getDate(final String dateString) {
		final Calendar c = Calendar.getInstance();
		final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			c.setTime(df.parse(dateString));
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return c;
	}

	private static void saveProcess(final Process process) {
		try {
			processService.create(process);
			assertNotNull(process.getId());
			LOG.info("#### Process created successfully");
		} catch (final IllegalArgumentException e) {
			LOG.warning(e.getMessage());
		} catch (final HibernateException e) {
			LOG.severe("#### Error saving new process: " + e.getMessage());
			fail(e.getMessage());
		}
	}

}
