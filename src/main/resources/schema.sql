--
-- Table structure for table `class`
--

CREATE TABLE `class` (
  `user_id` varchar(255) NOT NULL,
  `json` varchar(65000) DEFAULT NULL
) DEFAULT CHARSET=latin1;

--
-- Table structure for table `schedule`
--

CREATE TABLE `schedule` (
  `user_id` varchar(255) NOT NULL,
  `json` varchar(65000) DEFAULT NULL
) DEFAULT CHARSET=latin1;

--
-- Indexes for table `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`user_id`);
COMMIT;
