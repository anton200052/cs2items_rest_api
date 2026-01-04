# CS2 Items REST API

![Java](https://img.shields.io/badge/Java-Spring_Boot-orange)

**CS2Items REST API** is a high-performance backend service designed to aggregate and serve the complete database of Counter-Strike 2 items. It functions as an **intelligent caching layer** between limited third-party data providers and high-load internal applications (like the Market Parser).

---

## 🎯 The Problem & Solution

**The Challenge:**
Reliable market data providers often impose strict **rate limits** (e.g., limited requests per month) or pay-per-request models. Directly querying these services from a high-frequency trading bot would instantly exhaust the quota and incur high costs.

**The Solution:**
This service implements a **"Fetch Once, Serve Many"** strategy.
1.  It carefully downloads the entire item database from the third-party provider, respecting their limits.
2.  It persists this data into a local database.
3.  It serves this data to internal tools (Market Parser) via a fast, unlimited local REST API.

---

## ⚡ Key Features

* **Quota Optimization:** Drastically reduces external API calls. Instead of making 10,000 calls per day for item details, the system makes only a few calls to sync the database, saving money and API credits.
* **Data Normalization:** Converts raw external JSON data into a standardized internal format, ensuring consistent naming conventions across all your projects.
* **Low-Latency Access:** Since data is stored locally, response times for item lookups drop from ~200ms (external HTTP) to <5ms (internal DB).

---

## 🔗 Integration

This project serves as the central "Source of Truth" for the entire trading ecosystem.

---

## ⚠️ Disclaimer

This tool is for **educational purposes**. It is designed to optimize API usage and reduce load on third-party services, adhering to standard data caching practices.
