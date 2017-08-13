package com.github.eldnine.pagerank.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.eldnine.pagerank.repo.LinkRepo;
import com.github.eldnine.pagerank.repo.PageRepo;

@Component
public class RankCalcService {
	private static final Logger logger = LoggerFactory.getLogger(RankCalcService.class);
	
	private static final int num_iter = 10;
	
	@Autowired
	LinkRepo linkRepo;
	@Autowired
	PageRepo pageRepo;
	
	public List<Long> findDistinctFromId() {
		return linkRepo.findDistinctFromId();
	}
	public void run() {
		List<Long> fromIds = findDistinctFromId();
		List<Long> toIds = new ArrayList<Long>();
		List<long[]> links = new ArrayList<long[]>();
		List<Object[]> rawLinks = linkRepo.findDistinctByFromIdAndToId();
		logger.info("1111111");
		logger.info(rawLinks.size() + "");
		
		for (Object[] link : rawLinks) {
			long fromId = (long) link[0];
			
			long toId = (Long) link[1];
			if (fromId == toId || !fromIds.contains(fromId) || fromIds.contains(toId)) {
				continue;
			}
			links.add(new long[]{(long) link[0], (long) link[1]});
			if (!toIds.contains(toId)) {
				toIds.add(toId);
			}
		}
		Collections.sort(toIds);
		logger.info(toIds.toString());
		
		Map<Long, Double> prevRanks = new HashMap<Long, Double>();
		for (long node : fromIds) {
			double newRank = pageRepo.findNewRankById(node);
			prevRanks.put(node, newRank);
		}
		
		int numIter = num_iter;
		if (numIter < 1) {
			logger.warn("Wrong number of iterations.");
			return;
		}
		
		// do the pagerank in memory
		for (int i = 0; i < numIter; i++) {
			Map<Long, Double> nextRanks = new HashMap<Long, Double>();
			double total = 0.0;
			for (Map.Entry<Long, Double> entry : prevRanks.entrySet()) {
				long node = entry.getKey();
				double oldRank = entry.getValue();
				total += oldRank;
				nextRanks.put(node, 0.0);
				
				List<Long> giveIds = new ArrayList<Long>();
				for (long[] link : links) {
					if (link[0] != node) {
						// TODO
					}
				}
			}
			logger.info(total + "");
		}
	}
}
