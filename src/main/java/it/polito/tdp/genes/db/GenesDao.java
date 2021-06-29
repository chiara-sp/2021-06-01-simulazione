package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public void getAllGenes(Map<String,Genes> idMap){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		//List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				idMap.put(genes.getGeneId(), genes);
			}
			res.close();
			st.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	public List<Genes> getVertici(Map<String, Genes> idMap){
		String sql="select distinct g.`GeneID` as id "
				+ "from genes g "
				+ "where g.`Essential`=\"Essential\""; 
		List<Genes> result= new LinkedList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes g= idMap.get(res.getString("id"));
				if(g!=null) {
					result.add(g);
				}
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	
	}

	public List<Adiacenza> getArchi(Map<String, Genes> idMap){
		String sql="select i.`GeneID1` as id1, i.`GeneID2` as id2, i.`Expression_Corr` as peso "
				+ "from interactions i "
				+ "where i.`GeneID1`<>i.`GeneID2`";
		
		List<Adiacenza> result= new LinkedList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes g1= idMap.get(res.getString("id1"));
				Genes g2= idMap.get(res.getString("id2"));
				if(g1!=null && g2!=null) {
					result.add(new Adiacenza(g1,g2,res.getDouble("peso")));
				}
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
