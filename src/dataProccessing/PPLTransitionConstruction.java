package dataProccessing;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import data.pplSqlSchema.PPLSchema;
import data.pplTransition.AtomicChange;
import data.pplTransition.PPLTransition;
import data.pplTransition.TableChange;

public class PPLTransitionConstruction {
	
	private static TreeMap<Integer,PPLTransition> allPPLTransitions = new TreeMap<Integer,PPLTransition>();
	private static TreeMap<String,PPLSchema> allPPLSchemas = new TreeMap<String,PPLSchema>();
	private static TreeMap<String,TableChange> allTableChanges = new  TreeMap<String,TableChange>();

	public PPLTransitionConstruction(TreeMap<String,PPLSchema> tmpSc, TreeMap<String,TableChange> tmpTbs){
		
		allPPLSchemas=tmpSc;
		allTableChanges = tmpTbs;
		
	}
	
	public void makePPLTransitions(){
		/*
			Allaksa Delta.java
			
			Apo: res.setVersionNames(B.getName(), A.getName());
	
			Egine: res.setVersionNames(B.getName(), A.getName());
		
		*/
		
		allPPLTransitions  = new TreeMap<Integer,PPLTransition>();
		
		ArrayList<TableChange> tmpTableChanges = new ArrayList<TableChange>();
		
		Set<String> tmpKeys = allPPLSchemas.keySet();
		ArrayList<String> assistantKeys = new ArrayList<String>();
		
		for(String k: tmpKeys){
			assistantKeys.add(k);
		}
		
		for(int i=0; i<assistantKeys.size()-1; i++){
			
			PPLTransition tmpPPLTransition = new PPLTransition(assistantKeys.get(i),assistantKeys.get(i+1),i);
			
			allPPLTransitions.put(i,tmpPPLTransition);
			
		}
		
		
		for (Map.Entry<Integer,PPLTransition> tr : allPPLTransitions.entrySet()) {

			for (Map.Entry<String, TableChange> t : allTableChanges.entrySet()) {
				
				TableChange tmpTableChange = t.getValue();
				
				TreeMap<Integer,ArrayList<AtomicChange>> tmpAtomicChanges = tmpTableChange.getTableAtomicChanges();
				
				for (Map.Entry<Integer,ArrayList<AtomicChange>> at : tmpAtomicChanges.entrySet()) {
	
					if(at.getKey().equals(tr.getKey())){
					
						TableChange tmpTableChange1 = new TableChange(t.getKey(),tmpTableChange.getTableAtChForOneTransition(tr.getKey()));
						
						tmpTableChanges.add(tmpTableChange1);
						
					}
				
				}
					
			}
		
			tr.getValue().setTableChanges(tmpTableChanges);
			tmpTableChanges = new ArrayList<TableChange>();
			
		}

	}
	
	public  TreeMap<Integer,PPLTransition> getAllPPLTransitions(){
		
		return allPPLTransitions;
	}
}
