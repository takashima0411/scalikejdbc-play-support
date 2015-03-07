/*
 * Copyright 2015 scalikejdbc.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package scalikejdbc

import play.api.mvc._
import scala.concurrent.Future

class DBTxActionFunction(name: Any = 'default)(implicit context: ConnectionPoolContext = NoConnectionPoolContext) extends ActionFunction[Request, DBSessionRequest] {

  def invokeBlock[A](request: Request[A], block: DBSessionRequest[A] => Future[Result]) = {
    import TxBoundary.Future._
    NamedDB(name).localTx { session =>
      block(DBSessionRequest[A](session, request))
    }
  }

}
