package actors;

import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;
import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.Props;
import play.Logger;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TimeActor extends AbstractActorWithTimers {
	
	// unique set of all the userActors registered so far
	private Set<ActorRef> userActors;
	
	
	// operation 1
    public static final class Tick {
    	
    }
    
    // operation 2
    static public class RegisterMsg {
    	
    }

    static public Props getProps() {
    	return Props.create(TimeActor.class, () -> new TimeActor());
    }	

    private TimeActor() {
    	this.userActors = new HashSet<>();
    }
    
    // implementation of a timer with duration of 5 seconds
    @Override
    public void preStart() {
    	Logger.info("TimeActor {} started", self());
    	getTimers().startPeriodicTimer("Timer", new Tick(), Duration.create(15, TimeUnit.SECONDS));
    }

    // Registering the UserActor
    // When the TimeActor receives this message from the UserActor
    // it simply adds the user actor to a private field, e.g.,  userActors:
    @Override
    public Receive createReceive() {
        return receiveBuilder()
        		.match(RegisterMsg.class, msg -> userActors.add(sender()))
        		.match(Tick.class, msg -> notifyClients())
                .build();
    }

    // Every time this TimeActor receives a Tick message, it will go through its set of 
    // userActors and send each of them a message with the current (server) time
	private void notifyClients() {
    	//UserActor.TimeMessage tMsg = new UserActor.TimeMessage(LocalDateTime.now().toString());
    	//userActors.forEach(ar -> ar.tell(tMsg, self()));
    	UserActor.checkForUpdates cfp = new UserActor.checkForUpdates();
    	userActors.forEach(ar -> ar.tell(cfp, self()));
	}
}
