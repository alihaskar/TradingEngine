/*
 * Copyright (c)  Hoddmimes Solution AB 2021.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hoddmimes.te.instrumentctl;

import com.google.gson.JsonObject;
import com.hoddmimes.te.messages.SID;
import com.hoddmimes.te.messages.generated.Symbol;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class SymbolX extends Symbol
{
	private MarketX mMarket;
	private SID mSID;

	SymbolX(JsonObject pJsonObject, MarketX pMarket ) {
		super( pJsonObject.toString() );
		mMarket = pMarket;
		mSID = new SID( pMarket.getId().get(), pJsonObject.get("symbol").getAsString() );
		super.setSid( mSID.toString());
		super.setName( pJsonObject.get("symbol").getAsString());
	}

	public String getId() {
		return super.getSid().get();
	}


	public  boolean isOpen() {
		if (!super.getSuspended().isEmpty()) {
			return super.getSuspended().get();
		}
		return (!mMarket.isClosed());
	}




	private boolean isTickSizeAligned( long pPrice ) {
		if (super.getTickSize().get() == 0) {
			return true;
		}

		long tRest = pPrice - ((pPrice / 10000) * 10000);
		long tTickSize =  super.getTickSize().get();
		return ((tRest % tTickSize) == 0);
	}



	public void validate( long pPrice, long pLastKnownTradingPrice ) throws Exception {
		// Validate that price is a multiple of the tick size
		if (!isTickSizeAligned( pPrice )) {
			throw new Exception("order price is not tick size aligned");
		}

		if (!super.getMinPricePctChg().isEmpty()) {
			if ((super.getMinPricePctChg().get() != 0.0d) && (pLastKnownTradingPrice != 0)) {
				double p = (super.getMinPricePctChg().get() / 100.0d);
				long tCompPrice = (long) ((double) pLastKnownTradingPrice - ((double) pLastKnownTradingPrice * p));
				if (pPrice < tCompPrice) {
					throw new Exception("order price is outside (min) price limit " + super.getMinPricePctChg().get() + " %");
				}
			}
		} else {
			if ((mMarket.getMinPricePctChg().get() != 0.0d) && (pLastKnownTradingPrice != 0)) {
				double p = (mMarket.getMinPricePctChg().get() / 100.0d);
				long tCompPrice = (long) ((double) pLastKnownTradingPrice - ((double) pLastKnownTradingPrice * p));
				if (pPrice < tCompPrice) {
					throw new Exception("order price is outside (min) price limit " + mMarket.getMinPricePctChg().get() + " %");
				}
			}
		}

		if (!super.getMaxPricePctChg().isEmpty()) {
			if ((super.getMaxPricePctChg().get() != 0.0d) && (pLastKnownTradingPrice != 0)) {
				double p = (super.getMaxPricePctChg().get() / 100.0d);
				long tCompPrice = (long) ((double) pLastKnownTradingPrice + ((double) pLastKnownTradingPrice * p));
				if (pPrice > tCompPrice) {
					throw new Exception("order price is outside (max) price limit " + super.getMaxPricePctChg().get() + " %");
				}
			}
		} else {
			if ((mMarket.getMaxPricePctChg().get() != 0.0d) && (pLastKnownTradingPrice != 0)) {
				double p = (mMarket.getMaxPricePctChg().get() / 100.0d);
				long tCompPrice = (long) ((double) pLastKnownTradingPrice + ((double) pLastKnownTradingPrice * p));
				if (pPrice > tCompPrice) {
					throw new Exception("order price is outside (max) price limit " + mMarket.getMaxPricePctChg().get() + " %");
				}
			}
		}
	}

}
